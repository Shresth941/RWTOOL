package RwTool.rwtool.repo;

import RwTool.rwtool.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByOrderByGeneratedDateDesc();
    List<Report> findByNameContainingIgnoreCase(String name);
    List<Report> findByGeneratedDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<Report> findByPath(String path);
}
