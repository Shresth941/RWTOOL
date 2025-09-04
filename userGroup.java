package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_groups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name", unique = true, nullable = false)
    private String name;

    // In a more advanced setup, you could store permissions here as a JSONB field
    // @Column(columnDefinition = "jsonb")
    // private String permissions;
}

