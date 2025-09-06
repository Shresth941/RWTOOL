package RwTool.rwtool.service;

import RwTool.rwtool.dto.IngestRequest;
import RwTool.rwtool.dto.ReportRequest;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IngestService {

    private final ReportService reportService;
    private final ReportRepository reportRepository;
    private final AuditLogService auditLogService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Value("${file.incoming-dir}")
    private String incomingDir;

    @Value("${file-upload-dir}")
    private String uploadBaseDir;

    public IngestService(ReportService reportService,
                         ReportRepository reportRepository,
                         AuditLogService auditLogService,
                         NotificationService notificationService,
                         UserRepository userRepository) {
        this.reportService = reportService;
        this.reportRepository = reportRepository;
        this.auditLogService = auditLogService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void process(IngestRequest req) {
        String fileName = Paths.get(req.getInputFileName()).getFileName().toString();

        // --- sanitize filename ---
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("Invalid filename: " + fileName);
        }

        Path baseDir = Paths.get(uploadBaseDir).toAbsolutePath().normalize();
        Path targetDir = baseDir.resolve(req.getOutputFolderPath() == null ? "" : req.getOutputFolderPath()).normalize();

        try {
            Files.createDirectories(targetDir);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create target directory: " + targetDir, ex);
        }

        Path target = targetDir.resolve(fileName).normalize();

        // ensure confinement
        if (!target.startsWith(baseDir)) {
            throw new IllegalArgumentException("Invalid target path: " + target);
        }

        // action handling
        if ("New".equalsIgnoreCase(req.getAction())) {
            handleNew(fileName, target, req);
        } else if ("Delete".equalsIgnoreCase(req.getAction())) {
            handleDelete(fileName, target, req);
        } else {
            throw new IllegalArgumentException("Unsupported action: " + req.getAction());
        }
    }

    private void handleNew(String fileName, Path target, IngestRequest req) {
        Path src = Paths.get(incomingDir, fileName).toAbsolutePath().normalize();
        if (!Files.exists(src)) throw new NotFoundException("Source file not found: " + src);

        try {
            // atomic copy
            Path temp = Files.createTempFile(target.getParent(), "ingest-", ".tmp");
            Files.copy(src, temp, StandardCopyOption.REPLACE_EXISTING);
            try {
                Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            } finally {
                Files.deleteIfExists(temp);
            }

            // persist Report metadata
            ReportRequest rr = new ReportRequest();
            rr.setName(fileName);
            rr.setPath(target.toAbsolutePath().toString());
            rr.setGeneratedDate(LocalDateTime.now());
            rr.setUploadedBy(req.getUploadedBy());
            reportService.addReport(rr);

            // audit + notify
            Long uploaderId = resolveUserId(req.getUploadedBy());
            auditLogService.log(uploaderId, "REPORT_INGESTED",
                    "Ingested file: " + fileName + " → " + target.toAbsolutePath());
            if (uploaderId != null) {
                notificationService.createForUser(
                        uploaderId,
                        "New report available",
                        "Report " + fileName + " is now available",
                        "/reports"
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to ingest file: " + e.getMessage(), e);
        }
    }

    private void handleDelete(String fileName, Path target, IngestRequest req) {
        try {
            Files.deleteIfExists(target);

            Optional<Report> byPath = reportRepository.findByPath(target.toAbsolutePath().toString());
            byPath.ifPresent(reportRepository::delete);

            Long uploaderId = resolveUserId(req.getUploadedBy());
            auditLogService.log(uploaderId, "REPORT_DELETED",
                    "Deleted report: " + fileName + " from " + target.getParent());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    private Long resolveUserId(String email) {
        if (email == null) return null;
        Optional<User> opt = userRepository.findByEmail(email);
        return opt.map(User::getUserId).orElse(null);
    }
}
