package com.wealthcore.service;

import com.wealthcore.entity.Report;
import com.wealthcore.entity.ReportType;
import com.wealthcore.entity.User;
import com.wealthcore.repository.ReportRepository;
import com.wealthcore.repository.ReportTypeRepository;
import com.wealthcore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final AuditService auditService;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    // Constructor to inject all necessary dependencies
    public AdminService(ReportRepository reportRepository,
                        ReportTypeRepository reportTypeRepository,
                        AuditService auditService,
                        UserRepository userRepository,
                        FileStorageService fileStorageService) {
        this.reportRepository = reportRepository;
        this.reportTypeRepository = reportTypeRepository;
        this.auditService = auditService;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    // ... your other existing service methods for user management ...

    /**
     * Handles the complete business logic for an intelligent manual file upload.
     * It finds an existing ReportType based on the filename or creates a new one.
     * The @Transactional annotation is crucial: it ensures that all database operations
     * in this method either succeed together or fail together, preventing partial data saves.
     */
    @Transactional
    public Report intelligentUpload(MultipartFile file, UUID uploaderId) {
        // 1. Verify the uploader exists in the database.
        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new EntityNotFoundException("Uploader (Admin) not found with id: " + uploaderId));

        // 2. Find or Create the ReportType based on the file's name.
        ReportType reportType = findOrCreateReportType(file.getOriginalFilename());

        // 3. Store the physical file using the dedicated FileStorageService.
        String storedFilePath = fileStorageService.store(file, reportType);

        // 4. Create a new record for the 'reports' table.
        Report newReport = new Report();
        newReport.setFileName(file.getOriginalFilename());
        newReport.setReportType(reportType); // Link to the found or created type
        newReport.setUploadedBy(uploader);   // Link to the admin who uploaded it
        newReport.setFileStoragePath(storedFilePath);
        newReport.setGeneratedDate(Instant.now());

        // 5. Save the new report record to the database.
        Report savedReport = reportRepository.save(newReport);

        // 6. Log this action for auditing purposes.
        auditService.logAction("MANUAL_REPORT_UPLOADED", uploaderId, "Uploaded file: " + file.getOriginalFilename());

        return savedReport;
    }

    /**
     * A private helper method that encapsulates the "Find or Create" logic for ReportType.
     * This keeps the main service method cleaner and easier to read.
     */
    private ReportType findOrCreateReportType(String fileName) {
        // Define a simple rule to parse a clean name from the file.
        String parsedName = parseNameFromFileName(fileName);

        // Search the database for a report type with the parsed name.
        Optional<ReportType> existingType = reportTypeRepository.findByName(parsedName);

        if (existingType.isPresent()) {
            // If it exists, return the one we found.
            return existingType.get();
        } else {
            // If it does NOT exist, create a new one, save it, and then return it.
            ReportType newType = new ReportType();
            newType.setName(parsedName);
            // sourcePath can be null for types created via manual upload.
            newType.setSourcePath(null); 
            return reportTypeRepository.save(newType);
        }
    }

    /**
     * A helper method for the name parsing logic. This can be customized
     * to be as simple or complex as your business rules require.
     */
    private String parseNameFromFileName(String fileName) {
        String baseName = fileName;
        // Example rule: take the text before the first underscore or period.
        if (fileName.contains("_")) {
            baseName = fileName.substring(0, fileName.indexOf("_"));
        } else if (fileName.contains(".")) {
            baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        // Sanitize and standardize the name (e.g., "KYC" becomes "KYC Reports")
        return baseName.replaceAll("[^a-zA-Z0-9\\s]", "").trim() + " Reports";
    }
}

