package RwTool.rwtool.repo;

import RwTool.rwtool.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // note: this mirrors the repository method in your upload
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);
}
