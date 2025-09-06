package RwTool.rwtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportResponse {
    private Long id;
    private String title;
    private String content;
    private String reportType;
    private LocalDate reportDate;
    private String category;
    private Long userId;
    private String userFullName;
    private Long roleId;
    private String roleName;
}
