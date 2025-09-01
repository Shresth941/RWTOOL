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
import java.util.UUID;

@Service
public class AdminService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final AuditService auditService;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService; // <-- NEW dependency

    public AdminService(ReportRepository reportRepository,
                        ReportTypeRepository reportTypeRepository,
                        AuditService auditService,
                        UserRepository userRepository,
                        FileStorageService fileStorageService) { // <-- Inject the new service
        this.reportRepository = reportRepository;
        this.reportTypeRepository = reportTypeRepository;
        this.auditService = auditService;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    // ... your other service methods ...

    /**
     * Handles the business logic for a manual file upload by an admin.
     */
    @Transactional // Ensures that if any step fails, the entire transaction is rolled back.
    public Report manualUpload(MultipartFile file, UUID reportTypeId, UUID uploaderId) {
        // Step D: Basic validation is handled by the FileStorageService

        // Step F: Fetch the ReportType entity
        ReportType reportType = reportTypeRepository.findById(reportTypeId)
                .orElseThrow(() -> new EntityNotFoundException("ReportType not found with id: " + reportTypeId));

        // Fetch the User who is uploading
        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new EntityNotFoundException("Uploader (Admin) not found with id: " + uploaderId));

        // Call the dedicated service to store the file and get its path
        String storedFilePath = fileStorageService.store(file, reportType);

        // Step M & N: Create and populate the new Report entity
        Report newReport = new Report();
        newReport.setFileName(file.getOriginalFilename()); // Store the original name for display
        newReport.setReportType(reportType);
... (message truncated)
