package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles",
       indexes = {@Index(name = "idx_role_name", columnList = "name")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // optional JSON/permissions string (postgres jsonb recommended if using Postgres)
    @Column(name = "permissions", columnDefinition = "text")
    private String permissions;
}
