package com.wealthcore.repository;

import com.wealthcore.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, UUID> {

    /**
     * Finds a ReportType by its name. Spring Data JPA automatically creates the
     * necessary SQL query for this method based on its name. This is essential
     * for the "find or create" logic in the AdminService.
     *
     * @param name The name of the report type to search for.
     * @return An Optional containing the ReportType if found, or an empty Optional otherwise.
     */
    Optional<ReportType> findByName(String name);
}

