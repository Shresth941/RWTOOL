package RwTool.rwtool.service;

import RwTool.rwtool.dto.UserReportRequest;
import RwTool.rwtool.dto.UserReportResponse;
import RwTool.rwtool.entity.Role;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.entity.UserReport;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.RoleRepository;
import RwTool.rwtool.repo.UserReportRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserReportService {
    private final UserReportRepository repo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserReportService(UserReportRepository repo, UserRepository userRepo, RoleRepository roleRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public UserReportResponse create(UserReportRequest req) {
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + req.getUserId()));

        Role role = null;
        if (req.getRoleId() != null) {
            role = roleRepo.findById(req.getRoleId()).orElseThrow(() -> new NotFoundException("Role not found with id: " + req.getRoleId()));
        }

        UserReport ur = new UserReport();
        ur.setTitle(req.getTitle());
        ur.setContent(req.getContent());
        ur.setReportType(req.getReportType());
        ur.setReportDate(req.getReportDate());
        ur.setCategory(req.getCategory());
        ur.setUser(user);
        ur.setRole(role);

        UserReport saved = repo.save(ur);
        return map(saved);
    }

    public List<UserReportResponse> getByUser(Long userId) {
        return repo.findByUserId(userId).stream().map(this::map).collect(Collectors.toList());
    }

    public List<UserReportResponse> getByRole(String roleName) {
        return repo.findByRoleName(roleName).stream().map(this::map).collect(Collectors.toList());
    }

    public List<UserReportResponse> findByCriteria(String type, java.time.LocalDate date) {
        if (type != null && date != null) {
            return repo.findByReportTypeAndReportDate(type, date).stream().map(this::map).collect(Collectors.toList());
        } else if (type != null) {
            return repo.findByReportType(type).stream().map(this::map).collect(Collectors.toList());
        } else if (date != null) {
            return repo.findByReportDate(date).stream().map(this::map).collect(Collectors.toList());
        } else {
            return repo.findAll().stream().map(this::map).collect(Collectors.toList());
        }
    }

    private UserReportResponse map(UserReport ur) {
        return new UserReportResponse(ur.getId(), ur.getTitle(), ur.getContent(), ur.getReportType(), ur.getReportDate(), ur.getCategory(),
                ur.getUser().getUserId(), ur.getUser().getFullName(),
                ur.getRole() != null ? ur.getRole().getRoleId() : null,
                ur.getRole() != null ? ur.getRole().getName() : null);
    }
}
