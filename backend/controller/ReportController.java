package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.ReportRequest;
import RwTool.rwtool.dto.ReportResponse;
import RwTool.rwtool.service.ReportService;
import RwTool.rwtool.service.ZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ZipService zipService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReportResponse>>> listReports(Pageable pageable) {
        Page<ReportResponse> page = reportService.getReports(pageable);
        return ResponseEntity.ok(ApiResponse.success("Reports fetched", page));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> search(@RequestParam("q") String q) {
        List<ReportResponse> r = reportService.searchReports(q);
        return ResponseEntity.ok(ApiResponse.success("Search results", r));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ReportResponse>>> filterByDate(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            Pageable pageable) {

        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            Page<ReportResponse> page = reportService.filterReportsByDate(fromDate, toDate, pageable);
            return ResponseEntity.ok(ApiResponse.success("Filtered reports", page));
        } catch (DateTimeParseException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid date format. Use YYYY-MM-DD"));
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadZip(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {
        try {
            File zip = zipService.createZipForDateRange(from, to);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zip));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(zip.getName()).build());
            headers.setContentLength(zip.length());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Zip creation failed: " + ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponse>> createReport(@RequestBody ReportRequest req) {
        ReportResponse created = reportService.createReport(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Report created", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportResponse>> getReport(@PathVariable Long id) {
        ReportResponse r = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success("Report fetched", r));
    }
}
