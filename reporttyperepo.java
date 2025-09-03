package RwTool.rwtool.repo;

import RwTool.rwtool.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, UUID> {
    Optional<ReportType> findByName(String name);
}
