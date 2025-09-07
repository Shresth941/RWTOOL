package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.service.IngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ingest")
@RequiredArgsConstructor
public class IngestController {

    private final IngestService ingestService;

    /**
     * Upload file + metadata.
     * Accepts multipart file and metadata params (uniqueId, action, fileType, outputFolderPath).
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "uniqueId", required = false) String uniqueId,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "fileType", required = false) String fileType,
            @RequestParam(value = "outputFolderPath", required = false) String outputFolderPath) {

        try {
            String storedPath = ingestService.storeAndCreateReportRecord(file, uniqueId, action, fileType, outputFolderPath);
            return ResponseEntity.ok(ApiResponse.success("File uploaded", storedPath));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Upload failed: " + ex.getMessage()));
        }
    }

    /**
     * Manual trigger: move/process a file from source to destination.
     * Admin-only (optionally protected by @PreAuthorize in security config).
     */
    @PostMapping("/process")
    public ResponseEntity<ApiResponse<String>> process(@RequestParam String sourcePath, @RequestParam String destinationPath) {
        try {
            ingestService.processFile(sourcePath, destinationPath);
            return ResponseEntity.ok(ApiResponse.success("Processing triggered", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ex.getMessage()));
        }
    }
}
