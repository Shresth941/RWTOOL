package RwTool.rwtool.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String fullname;
    private String email;
    private String password; // Only used when creating a new user
    // Add other fields as needed, like roleId
}
