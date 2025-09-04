package RwTool.rwtool.repo;

import RwTool.rwtool.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
    Optional<ReportType> findByName(String name);
}
