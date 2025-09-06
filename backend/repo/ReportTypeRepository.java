package RwTool.rwtool.repo;

import RwTool.rwtool.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
    ReportType findByName(String name);
}
