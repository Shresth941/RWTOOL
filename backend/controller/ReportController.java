package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ReportRequest;
import RwTool.rwtool.dto.ReportResponse;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.service.AuditLogService;
import RwTool.rwtool.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService service;
    private final ReportRepository reportRepository;
    private final AuditLogService auditLogService;

    @Value("${file-upload-dir}")
    private String uploadBaseDir;

    public ReportController(ReportService service,
                            ReportRepository reportRepository,
                            AuditLogService auditLogService) {
        this.service = service;
        this.reportRepository = reportRepository;
        this.auditLogService = auditLogService;
    }

    /* -----------------------
       Public / authenticated endpoints
       ----------------------- */

    @GetMapping
    public ResponseEntity<List<ReportResponse>> all() {
        return ResponseEntity.ok(service.getAllReports());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Report>> allPaged(Pageable pageable) {
        return ResponseEntity.ok(service.getAllReportsPaged(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReportResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(service.searchByName(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> get(@PathVariable Long id) {
        Report r = service.getEntityById(id);
        return ResponseEntity.ok(new ReportResponse(
                r.getId(),
                r.getName(),
                r.getPath(),
                r.getGeneratedDate(),
                r.getUploadedBy(),
                r.getReportType() != null ? r.getReportType().getReportTypeId() : null
        ));
    }

    /**
     * Download reports by date range as a streaming ZIP.
     * Example: /api/reports/download?from=2025-09-01&to=2025-09-03
     */
    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        List<Report> reports = reportRepository.findByGeneratedDateBetween(start, end);

        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 if no reports
        }

        StreamingResponseBody stream = outputStream -> {
            try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
                for (Report report : reports) {
                    Path path = Paths.get(report.getPath());
                    if (Files.exists(path) && Files.isRegularFile(path)) {
                        try {
                            ZipEntry entry = new ZipEntry(path.getFileName().toString());
                            zos.putNextEntry(entry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (Exception ex) {
                            logger.error("Failed to add file to ZIP: {}", path, ex);
                            auditLogService.log(null, "REPORT_DOWNLOAD_FAILED",
                                    "Failed to add report " + report.getId() + " to ZIP: " + ex.getMessage());
                        }
                    } else {
                        logger.warn("Missing file for report {} at path {}", report.getId(), path);
                        auditLogService.log(null, "REPORT_MISSING_FILE",
                                "Report " + report.getId() + " missing file at " + path);
                    }
                }
                zos.finish();
            }
        };

        String zipName = String.format("reports_%s_to_%s.zip", from, to);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream);
    }

    /* -----------------------
       Admin-protected endpoints
       ----------------------- */

    @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @PostMapping
    public ResponseEntity<ReportResponse> create(@RequestBody ReportRequest req) {
        ReportResponse created = service.addReport(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReportResponse> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy,
            @RequestParam(value = "generatedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime generatedDate,
            @RequestParam(value = "reportTypeId", required = false) Long reportTypeId
    ) throws Exception {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // sanitize filename
        String original = Paths.get(file.getOriginalFilename()).getFileName().toString();
        if (original.contains("..") || original.contains("/") || original.contains("\\")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Path uploadDir = Paths.get(uploadBaseDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);

        String storedFileName = System.currentTimeMillis() + "_" + original;
        Path target = uploadDir.resolve(storedFileName).normalize();

        if (!target.startsWith(uploadDir)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // atomic save
        Path temp = Files.createTempFile(uploadDir, "upload-", ".tmp");
        try {
            Files.copy(file.getInputStream(), temp, StandardCopyOption.REPLACE_EXISTING);
            try {
                Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }

        ReportRequest req = new ReportRequest();
        req.setName(original);
        req.setPath(target.toAbsolutePath().toString());
        req.setGeneratedDate(generatedDate);
        req.setUploadedBy(uploadedBy);
        req.setReportTypeId(reportTypeId);

        ReportResponse resp = service.addReport(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> update(@PathVariable Long id, @RequestBody ReportRequest req) {
        ReportResponse updated = service.updateReport(id, req);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Report r = service.getEntityById(id);
        try {
            Path p = Paths.get(r.getPath());
            if (Files.exists(p) && Files.isRegularFile(p)) {
                Files.delete(p);
            }
        } catch (Exception ex) {
            logger.warn("Failed to delete physical file for report {}: {}", id, ex.getMessage());
        }

        service.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/recent/{userId}")
public ResponseEntity<List<String>> recentReports(@PathVariable Long userId) {
    List<String> recent = auditLogService.getRecentReportsByUser(userId);
    if (recent.isEmpty()) {
        return ResponseEntity.noContent().build(); // 204 if no recent reports
    }
    return ResponseEntity.ok(recent);
}

}
