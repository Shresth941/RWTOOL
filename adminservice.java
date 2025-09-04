package RwTool.rwtool.services;

import RwTool.rwtool.entity.*;
import RwTool.rwtool.exception.ResourceNotFoundException;
import RwTool.rwtool.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final ReportRepository reportRepository;
    private final FileStorageService fileStorageService;

    public AdminService(UserRepository uRepo, ReportTypeRepository rtRepo, ReportRepository rRepo, FileStorageService fs) {
        this.userRepository = uRepo;
        this.reportTypeRepository = rtRepo;
        this.reportRepository = rRepo;
        this.fileStorageService = fs;
    }

    // --- User Management ---
    public List<UserEntity> getAllUsers() { return userRepository.findAll(); }

    public UserEntity createUser(UserEntity newUser) {
        // Add logic here to check if user with email already exists to avoid errors
        return userRepository.save(newUser);
    }
    
    public UserEntity updateUser(Long userId, UserEntity userDetails) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setFullname(userDetails.getFullname());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // --- Report Type (Rule) Management ---
    public List<ReportType> getAllReportTypes() { return reportTypeRepository.findAll(); }

    public ReportType createReportType(ReportType newReportType) { return reportTypeRepository.save(newReportType); }

    public ReportType updateReportType(Long typeId, ReportType typeDetails) {
        ReportType type = reportTypeRepository.findById(typeId)
            .orElseThrow(() -> new ResourceNotFoundException("ReportType not found with id: " + typeId));
        type.setName(typeDetails.getName());
        type.setSourcePath(typeDetails.getSourcePath());
        return reportTypeRepository.save(type);
    }

    public void deleteReportType(Long typeId) {
        if (!reportTypeRepository.existsById(typeId)) {
            throw new ResourceNotFoundException("ReportType not found with id: " + typeId);
        }
        reportTypeRepository.deleteById(typeId);
    }

    // --- Intelligent File Upload Logic ---
    @Transactional
    public Report intelligentUpload(MultipartFile file, Long uploaderId) {
        UserEntity uploader = userRepository.findById(uploaderId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin uploader not found with id: " + uploaderId));
        ReportType reportType = findOrCreateReportType(file.getOriginalFilename());
        String storedFilePath = fileStorageService.store(file, reportType);

        Report newReport = new Report();
        newReport.setFileName(file.getOriginalFilename());
        newReport.setReportType(reportType);
        newReport.setUploadedBy(uploader.getFullname());
        newReport.setFileStoragePath(storedFilePath);
        newReport.setGeneratedDate(LocalDateTime.now());
        
        return reportRepository.save(newReport);
    }

    private ReportType findOrCreateReportType(String fileName) {
        String parsedName = parseNameFromFileName(fileName);
        return reportTypeRepository.findByName(parsedName).orElseGet(() -> {
            ReportType newType = new ReportType();
            newType.setName(parsedName);
            return reportTypeRepository.save(newType);
        });
    }

    private String parseNameFromFileName(String fileName) {
        String baseName = fileName;
        if (fileName.contains("_")) baseName = fileName.substring(0, fileName.indexOf("_"));
        else if (fileName.contains(".")) baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        return baseName.replaceAll("[^a-zA-Z0-9\\s]", "").trim() + " Reports";
    }
}
