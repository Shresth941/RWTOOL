package RwTool.rwtool.service;

import RwTool.rwtool.dto.UserRequest;
import RwTool.rwtool.dto.UserResponse;
import RwTool.rwtool.entity.Role;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.exceptions.AlreadyExistsException;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.RoleRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    // if you still need favoriteRepo/reportRepo inject them as before

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(UserRequest req) {
        if (!StringUtils.hasText(req.getEmail()) || !StringUtils.hasText(req.getPassword())) {
            throw new IllegalArgumentException("Email and password are required");
        }
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new AlreadyExistsException("User with email " + req.getEmail() + " already exists");
        }

        Role role = roleRepo.findByName(req.getRoleName());
        if (role == null) {
            role = new Role();
            role.setName(req.getRoleName());
            role = roleRepo.save(role);
        }

        User u = new User();
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword())); // <-- BCrypt via bean
        u.setRole(role);
        u.setActive(true);
        u.setLastLogin(LocalDateTime.now());

        User saved = userRepo.save(u);
        // map saved to UserResponse (you can keep mapping helper)
        return new UserResponse(saved.getUserId(), saved.getFullName(), saved.getEmail(),
                saved.getRole().getName(), saved.getLastLogin(), saved.isActive(), Set.of());
    }

    public User getUserEntity(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public UserResponse getById(Long id) {
        return userRepo.findById(id).map(this::mapToResponse).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<UserResponse> getByRole(String roleName) {
        return userRepo.findByRole_Name(roleName).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void toggleFavorite(Long userId, Long reportId) {
        User user = getUserEntity(userId);
        Report report = reportRepo.findById(reportId).orElseThrow(() -> new NotFoundException("Report not found with id: " + reportId));

        favoriteRepo.findByUser_UserIdAndReport_Id(userId, reportId).ifPresentOrElse(
                fav -> {
                    favoriteRepo.deleteByUser_UserIdAndReport_Id(userId, reportId);
                },
                () -> {
                    Favorite fav = new Favorite();
                    fav.setUser(user);
                    fav.setReport(report);
                    fav.setBookmarkedAt(LocalDateTime.now());
                    favoriteRepo.save(fav);
                }
        );
    }

    public Set<Long> getFavoriteReportIds(Long userId) {
        User u = getUserEntity(userId);
        return u.getFavorites().stream().map(f -> f.getReport().getId()).collect(Collectors.toSet());
    }

    private String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    private UserResponse mapToResponse(User u) {
        Set<Long> favIds = u.getFavorites().stream().map(f -> f.getReport().getId()).collect(Collectors.toSet());
        return new UserResponse(u.getUserId(), u.getFullName(), u.getEmail(), u.getRole().getName(),
                u.getLastLogin(), u.isActive(), favIds);
    }
}
