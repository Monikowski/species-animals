package studia.isa.monikowski.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.repository.AnimalRepository;
import studia.isa.monikowski.animal.repository.SpeciesRepository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private AnimalRepository animalRepository;
    private SpeciesRepository speciesRepository;

    @Autowired
    public AnimalService(AnimalRepository repository, SpeciesRepository speciesRepository) {
        this.animalRepository = repository;
        this.speciesRepository = speciesRepository;
    }

    public Optional<Animal> find(Long id) {return animalRepository.findById(id);}

    public Optional<Animal> find(Long id, String species_name){
        Optional<Species> species = speciesRepository.findById(species_name);
        if (species.isPresent()) {
            return animalRepository.findByIdAndSpecies(id, species.get());
        } else {
            return Optional.empty();
        }
    }

    public List<Animal> findAll() {return animalRepository.findAll();}

    public List<Animal> findAll(Species species) {return animalRepository.findAllBySpecies(species);}

    @Transactional
    public Animal create(Animal animal) {return animalRepository.save(animal);}

    @Transactional
    public void delete(Long id) {
        animalRepository.deleteById(id);}

    @Transactional
    public void update(Animal animal) {
        animalRepository.save(animal);}
}
