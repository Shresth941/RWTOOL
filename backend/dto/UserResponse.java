package RwTool.rwtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String roleName;
    private LocalDateTime lastLogin;
    private boolean isActive;
    private Set<Long> favoriteReportIds;
}
