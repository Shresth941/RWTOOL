package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_favorites",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "report_id"})},
       indexes = {@Index(name = "idx_fav_user", columnList = "user_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fav_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    // when the user bookmarked it
    @Column(name = "bookmarked_at", nullable = false)
    private LocalDateTime bookmarkedAt;
}
