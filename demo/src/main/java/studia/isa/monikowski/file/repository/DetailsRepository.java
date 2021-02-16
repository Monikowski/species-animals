package studia.isa.monikowski.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studia.isa.monikowski.file.entity.Details;

/**
 * Repository for species entity. Repositories should be used in business layer (e.g.: in services).
 */
@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {
}