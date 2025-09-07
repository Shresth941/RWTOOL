package RwTool.rwtool.service;

import RwTool.rwtool.dto.AuditLogDto;
import RwTool.rwtool.entity.AuditLog;
import RwTool.rwtool.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public List<AuditLogDto> getLogsForUser(Long userId) {
        return auditLogRepository.findByUser_UserIdOrderByTimestampDesc(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<AuditLogDto> getAllLogs() {
        return auditLogRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private AuditLogDto toDto(AuditLog a) {
        return AuditLogDto.builder()
                .id(a.getId())
                .action(a.getAction())
                .entityName(a.getEntityName())
                .entityId(a.getEntityId())
                .performedByUserId(a.getPerformedBy() != null ? a.getPerformedBy().getId() : null)
                .performedAt(a.getPerformedAt())
                .details(a.getDetails())
                .build();
    }
}
