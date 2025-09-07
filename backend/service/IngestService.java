package RwTool.rwtool.service;

import RwTool.rwtool.entity.Report;
import RwTool.rwtool.repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IngestService {

    private final FileStorageService fileStorageService;
    private final FileProcessorService fileProcessorService;
    private final ReportRepository reportRepository;

    /**
     * Store uploaded file and create a Report record (temporary path). Returns stored path.
     */
    @Transactional
    public String storeAndCreateReportRecord(MultipartFile file, String uniqueId, String action, String fileType, String outputFolderPath) throws IOException {
        String stored = fileStorageService.store(file);

        Report r = Report.builder()
                .title(file.getOriginalFilename())
                .description("Ingested file: " + file.getOriginalFilename())
                .fileName(file.getOriginalFilename())
                .filePath(stored)
                .fileType(fileType)
                .createdAt(LocalDateTime.now())
                .build();

        // optionally save request-specific metadata in another table if required
        reportRepository.save(r);
        return stored;
    }

    /**
     * Process file (move from source to destination). This method is used by Admin manual trigger.
     */
    public void processFile(String sourcePath, String destinationPath) throws IOException {
        // simple move
        fileProcessorService.moveFile(sourcePath, destinationPath);

        // update report record if a Report exists with sourcePath
        reportRepository.findByPath(sourcePath).ifPresent(r -> {
            r.setFilePath(destinationPath);
            reportRepository.save(r);
        });
    }
}
