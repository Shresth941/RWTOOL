package RwTool.rwtool.services;

import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.ReportTypeRepository;
import RwTool.rwtool.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    // private final AuditService auditService; // Remember to inject and use this

    public AdminService(ReportRepository reportRepository,
                        ReportTypeRepository reportTypeRepository,
                        UserRepository userRepository,
                        FileStorageService fileStorageService) {
        this.reportRepository = reportRepository;
        this.reportTypeRepository = reportTypeRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Report intelligentUpload(MultipartFile file, UUID uploaderId) {
        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new EntityNotFoundException("Uploader (Admin) not found with id: " + uploaderId));

        ReportType reportType = findOrCreateReportType(file.getOriginalFilename());

        String storedFilePath = fileStorageService.store(file, reportType);

        Report newReport = new Report();
        newReport.setFileName(file.getOriginalFilename());
        newReport.setReportType(reportType);
        newReport.setUploadedBy(uploader);
        newReport.setFileStoragePath(storedFilePath);
        newReport.setGeneratedDate(Instant.now());

        Report savedReport = reportRepository.save(newReport);

        // auditService.logAction("MANUAL_REPORT_UPLOADED", uploaderId, "Uploaded file: " + file.getOriginalFilename());

        return savedReport;
    }

    private ReportType findOrCreateReportType(String fileName) {
        String parsedName = parseNameFromFileName(fileName);
        Optional<ReportType> existingType = reportTypeRepository.findByName(parsedName);

        return existingType.orElseGet(() -> {
            ReportType newType = new ReportType();
            newType.setName(parsedName);
            newType.setSourcePath(null);
            return reportTypeRepository.save(newType);
        });
    }

    private String parseNameFromFileName(String fileName) {
        String baseName = fileName;
        if (fileName.contains("_")) {
            baseName = fileName.substring(0, fileName.indexOf("_"));
        } else if (fileName.contains(".")) {
            baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return baseName.replaceAll("[^a-zA-Z0-9\\s]", "").trim() + " Reports";
    }
}
