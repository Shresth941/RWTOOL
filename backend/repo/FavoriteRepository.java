package RwTool.rwtool.repo;

import RwTool.rwtool.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUser_UserIdAndReport_Id(Long userId, Long reportId);
    void deleteByUser_UserIdAndReport_Id(Long userId, Long reportId);
}
