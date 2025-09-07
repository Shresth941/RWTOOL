package RwTool.rwtool.service;

import RwTool.rwtool.dto.*;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final RoleService roleService;
    private final IngestService ingestService;
    private final ReportService reportService;

    // User operations
    public UserResponse createUser(UserRequest req) { return userService.createUser(req); }
    public UserResponse updateUser(Long id, UserRequest req) { return userService.updateUser(id, req); }
    public List<UserResponse> getAllUsers() { return userService.getAllUsers(); }
    public void deleteUser(Long id) { userService.deleteUser(id); }

    // Role operations
    public RoleDto createRole(RoleDto r) { return roleService.createRole(r); }
    public List<RoleDto> getAllRoles() { return roleService.getAllRoles(); }

    // Ingest manual trigger
    public void triggerIngestion(String filePath, String destination) {
        try {
            ingestService.processFile(filePath, destination);
        } catch (Exception ex) {
            throw new RuntimeException("Ingestion failed: " + ex.getMessage());
        }
    }

    // Reports overview (delegates to reportService)
    public List<ReportResponse> getAllReports() {
        // get first page large size; alternatively create reportService.getAll()
        return reportService.getReports(org.springframework.data.domain.PageRequest.of(0, 1000)).getContent();
    }
}
