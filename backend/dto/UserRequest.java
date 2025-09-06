package RwTool.rwtool.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;   // plain text → will be hashed in service
    private String roleName;   // e.g. "WEALTH_ADMIN"
}
