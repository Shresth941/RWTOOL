package RwTool.rwtool.service;

import RwTool.rwtool.dto.NotificationDto;
import RwTool.rwtool.entity.Notification;
import RwTool.rwtool.repo.NotificationRepository;
import RwTool.rwtool.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<NotificationDto> getNotificationsForUser(Long userId) {
        List<Notification> list = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification n = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setReadFlag(true);
        notificationRepository.save(n);
    }

    public NotificationDto toDto(Notification n) {
        return NotificationDto.builder()
                .id(n.getId())
                .userId(n.getUser() != null ? n.getUser().getId() : null)
                .title(n.getTitle())
                .message(n.getMessage())
                .readFlag(n.isReadFlag())
                .sentAt(n.getSentAt())
                .build();
    }
}
