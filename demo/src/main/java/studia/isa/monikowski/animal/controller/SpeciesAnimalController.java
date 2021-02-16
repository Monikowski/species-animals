package studia.isa.monikowski.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import studia.isa.monikowski.animal.dto.*;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.service.AnimalService;
import studia.isa.monikowski.animal.service.SpeciesService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/species_all/{species_name}/animals")
public class SpeciesAnimalController {
    private SpeciesService speciesService;
    private AnimalService animalService;

    @Autowired
    public SpeciesAnimalController(SpeciesService speciesService, AnimalService animalService) {
        this.speciesService = speciesService;
        this.animalService = animalService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<GetAnimalsResponse> getAnimals(@PathVariable("species_name") String species_name) {
        Optional<Species> species = speciesService.find(species_name);
        return species.map(value -> ResponseEntity.ok(GetAnimalsResponse.entityToDtoMapper().apply(animalService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<GetAnimalResponse> getAnimal(@PathVariable("species_name") String species_name,
                                                       @PathVariable("id") long id) {
        return animalService.find(id, species_name)
                .map(value -> ResponseEntity.ok(GetAnimalResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> createAnimal(@PathVariable("species_name") String species_name,
                                                @RequestBody CreateAnimalRequest request,
                                                UriComponentsBuilder builder) {
        Optional<Species> species = speciesService.find(species_name);
        request.setSpecies(species_name);
        if (species.isPresent()) {
            Animal animal = CreateAnimalRequest
                    .dtoToEntityMapper(name -> speciesService.find(name).orElseThrow())
                    .apply(request);
            animal = animalService.create(animal);
            return ResponseEntity.created(builder.pathSegment("api", "species_all", "{species_name}", "animals", "{id}")
                    .buildAndExpand(species.get().getSpecies_name(), animal.getId()).toUri()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("species_name") String species_name,
                                                @PathVariable("id") long id) {
        Optional<Animal> animal = animalService.find(id, species_name);
        if (animal.isPresent()) {
            animalService.delete(animal.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    public ResponseEntity<Void> updateAnimal(@PathVariable("species_name") String species_name,
                                                @RequestBody UpdateAnimalRequest request,
                                                @PathVariable("id") long id) {
        Optional<Animal> animal = animalService.find(id, species_name);
        if (animal.isPresent()) {
            UpdateAnimalRequest.dtoToEntityUpdater().apply(animal.get(), request);
            animalService.update(animal.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}