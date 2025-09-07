package RwTool.rwtool.repo;

import RwTool.rwtool.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    @Query("SELECT ur FROM UserReport ur WHERE ur.reportType = :type AND ur.reportDate = :date")
    List<UserReport> findByReportTypeAndReportDate(@Param("type") String type, @Param("date") LocalDate date);

    List<UserReport> findByReportType(String type);

    List<UserReport> findByReportDate(LocalDate date);

    @Query("SELECT ur FROM UserReport ur WHERE ur.user.userId = :userId")
    List<UserReport> findByUserId(@Param("userId") Long userId);

    @Query("SELECT ur FROM UserReport ur WHERE ur.role.name = :roleName")
    List<UserReport> findByRoleName(@Param("roleName") String roleName);
}
