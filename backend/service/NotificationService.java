package RwTool.rwtool.service;

import RwTool.rwtool.dto.NotificationDto;
import RwTool.rwtool.entity.Notification;
import RwTool.rwtool.repo.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto createForUser(Long userId, String title, String message, String link) {
        Notification n = new Notification();
        n.setRecipientId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setLink(link);
        n.setRead(false);
        n.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(n);
        return mapToDto(saved);
    }

    public List<NotificationDto> getForUser(Long userId, Pageable pageable) {
        Page<Notification> page = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId, pageable);
        return page.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void markRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    private NotificationDto mapToDto(Notification n) {
        return new NotificationDto(
                n.getId(),
                n.getTitle(),
                n.getMessage(),
                n.getLink(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}
