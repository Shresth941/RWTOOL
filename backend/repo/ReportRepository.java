package RwTool.rwtool.repo;

import RwTool.rwtool.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // recent-first listing
    List<Report> findAllByOrderByGeneratedDateDesc();

    // free-text search by file name
    List<Report> findByNameContainingIgnoreCase(String name);
    Page<Report> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // date-range queries
    List<Report> findByGeneratedDateBetween(LocalDateTime start, LocalDateTime end);
    Page<Report> findByGeneratedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // find by absolute storage path (unique)
    Optional<Report> findByPath(String path);
}
