package RwTool.rwtool.services;

import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.entity.UserEntity;
import RwTool.rwtool.exception.ResourceNotFoundException;
import RwTool.rwtool.repo.ReportTypeRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ReportTypeRepository reportTypeRepository;

    public AdminService(UserRepository userRepository, ReportTypeRepository reportTypeRepository) {
        this.userRepository = userRepository;
        this.reportTypeRepository = reportTypeRepository;
    }

    // --- User Management ---
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(UserEntity newUser) {
        // In a real app, you would check for existing email and hash the password here
        return userRepository.save(newUser);
    }

    public UserEntity updateUser(Long userId, UserEntity userDetails) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setFullname(userDetails.getFullname());
        user.setEmail(userDetails.getEmail());
        // Add other update logic as needed (e.g., updating roles)
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // --- Report Type (Rule) Management ---
    public List<ReportType> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }

    public ReportType createReportType(ReportType newReportType) {
        return reportTypeRepository.save(newReportType);
    }

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
}
