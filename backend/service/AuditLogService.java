package RwTool.rwtool.service;

import RwTool.rwtool.dto.AuditLogDto;
import RwTool.rwtool.entity.AuditLog;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.AuditLogRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogService {
    private final AuditLogRepository repo;
    private final UserRepository userRepo;

    public AuditLogService(AuditLogRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public AuditLogDto log(Long userId, String action, String details) {
        User u = null;
        if (userId != null) u = userRepo.findById(userId).orElse(null);
        AuditLog l = new AuditLog();
        l.setUser(u);
        l.setAction(action);
        l.setTimestamp(LocalDateTime.now());
        l.setDetails(details);
        AuditLog saved = repo.save(l);
        return new AuditLogDto(saved.getLogId(), u != null ? u.getUserId() : null, saved.getAction(), saved.getTimestamp(), saved.getDetails());
    }

    public List<AuditLogDto> getByUser(Long userId) {
        userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return repo.findByUser_UserIdOrderByTimestampDesc(userId).stream()
                .map(l -> new AuditLogDto(l.getLogId(), l.getUser() != null ? l.getUser().getUserId() : null, l.getAction(), l.getTimestamp(), l.getDetails()))
                .collect(Collectors.toList());
    }
    public List<AuditLogDto> getRecentReportsByUser(Long userId) {
        userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return repo.findByUser_UserIdOrderByTimestampDesc(userId).stream()
                .limit(10) // last 10 actions
                .map(l -> new AuditLogDto(
                        l.getLogId(),
                        l.getUser() != null ? l.getUser().getUserId() : null,
                        l.getAction(),
                        l.getTimestamp(),
                        l.getDetails()))
                .collect(Collectors.toList());
    }
    
}
