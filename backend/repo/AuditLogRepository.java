package RwTool.rwtool.repo;

import RwTool.rwtool.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUser_UserIdOrderByTimestampDesc(Long userId);
}
