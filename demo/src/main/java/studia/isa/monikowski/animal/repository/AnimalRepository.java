package studia.isa.monikowski.animal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;


import java.util.List;
import java.util.Optional;

/**
 * Repository for animal entity. Repositories should be used in business layer (e.g.: in services).
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Optional<Animal> findByIdAndSpecies(Long id, Species species);
    List<Animal> findAllBySpecies(Species species);
}
