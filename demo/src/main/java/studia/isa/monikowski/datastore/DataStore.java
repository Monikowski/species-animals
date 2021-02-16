package studia.isa.monikowski.datastore;

import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import studia.isa.monikowski.serialization.CloningUtility;

@Log
@Component
public class DataStore {

    private Set<Animal> animals = new HashSet<>();


    private Set<Species> species_list = new HashSet<>();

    public synchronized List<Animal> findAllAnimals() {
        return animals.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public Optional<Animal> findAnimal(long id) {
        return animals.stream()
                .filter(animal -> animal.getId() == id)
                .findFirst()
                .map(CloningUtility::clone);
    }


    public Stream<Animal> getAnimalStream() {
        return animals.stream();
    }

    public List<Animal> findAllOfSpecies(Species entity) {
        return getAnimalStream()
                .filter(animal -> animal.getSpecies().equals(entity))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createAnimal(Animal animal) throws IllegalArgumentException {
        animal.setId(findAllAnimals().stream().mapToLong(Animal::getId).max().orElse(0) + 1);
        animals.add(animal);
    }

    public synchronized void deleteAnimal(Long id) throws IllegalArgumentException {
        findAnimal(id).ifPresentOrElse(
                original -> animals.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The animal with id \"%d\" does not exist", id));
                });
    }

    public synchronized List<Species> findAllSpecies() {
        return species_list.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }


    public Optional<Species> findSpecies(String species_name) {
        return species_list.stream()
                .filter(aSpecies -> aSpecies.getSpecies_name().equals(species_name))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createSpecies(Species aSpecies) throws IllegalArgumentException {
        findSpecies(aSpecies.getSpecies_name()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The species name \"%s\" is not unique", aSpecies.getSpecies_name()));
                },
                () -> species_list.add(aSpecies));
    }

    public synchronized void deleteSpecies(String species_name) throws IllegalArgumentException {
        Optional<Species> aSpecies = findSpecies(species_name);
        if (aSpecies.isPresent()) {
            List<Long> animalIds = getAnimalStream()
                    .filter(animal -> animal.getSpecies().getSpecies_name().equals(species_name))
                    .map(Animal::getId)
                    .collect(Collectors.toList());
            if (animalIds.isEmpty()) {
                species_list.remove(aSpecies);
            } else {
                StringBuilder err = new StringBuilder(String.format("The species \"%s\" cannot be removed, because it appears in following animals: ", species_name));
                for (Long aId : animalIds) {
                    err.append(aId);
                    err.append(", ");
                }
                throw new IllegalArgumentException(err.toString());
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("The species \"%s\" does not exist: ", species_name)
            );
        }

    }
}

