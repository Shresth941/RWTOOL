package RwTool.rwtool.services;

import RwTool.rwtool.entity.*;
import RwTool.rwtool.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ReportTypeRepository reportTypeRepository;

    public AdminService(UserRepository uRepo, ReportTypeRepository rtRepo) {
        this.userRepository = uRepo;
        this.reportTypeRepository = rtRepo;
    }

    // --- User Management ---
    public List<UserEntity> getAllUsers() { return userRepository.findAll(); }

    public UserEntity createUser(UserEntity newUser) { /* ...logic to hash password and save... */ return userRepository.save(newUser); }
    
    public UserEntity updateUser(Long userId, UserEntity userDetails) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setFullname(userDetails.getFullname());
        user.setEmail(userDetails.getEmail());
        // Add logic to update role if needed
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) { userRepository.deleteById(userId); }

    // --- Report Type (Rule) Management ---
    public List<ReportType> getAllReportTypes() { return reportTypeRepository.findAll(); }

    public ReportType createReportType(ReportType newReportType) { return reportTypeRepository.save(newReportType); }

    public ReportType updateReportType(Long typeId, ReportType typeDetails) {
        ReportType type = reportTypeRepository.findById(typeId).orElseThrow(() -> new EntityNotFoundException("ReportType not found"));
        type.setName(typeDetails.getName());
        type.setSourcePath(typeDetails.getSourcePath());
        return reportTypeRepository.save(type);
    }

    public void deleteReportType(Long typeId) { reportTypeRepository.deleteById(typeId); }
}
