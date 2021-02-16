package studia.isa.monikowski.animal.controller;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import studia.isa.monikowski.animal.dto.CreateAnimalRequest;
import studia.isa.monikowski.animal.dto.GetAnimalResponse;
import studia.isa.monikowski.animal.dto.GetAnimalsResponse;
import studia.isa.monikowski.animal.dto.UpdateAnimalRequest;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.service.AnimalService;
import studia.isa.monikowski.animal.service.SpeciesService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/animals")
public class AnimalController {
    private AnimalService animalService;
    private SpeciesService speciesService;

    @Autowired
    public AnimalController(AnimalService animalService, SpeciesService speciesService) {
        this.animalService = animalService;
        this.speciesService = speciesService;
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<GetAnimalsResponse> getAnimals() {
        List<Animal> all = animalService.findAll();
        Function<Collection<Animal>, GetAnimalsResponse> mapper = GetAnimalsResponse.entityToDtoMapper();
        GetAnimalsResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<GetAnimalResponse> getAnimal(@PathVariable("id") long id) {
        return animalService.find(id)
               .map(value -> ResponseEntity.ok(GetAnimalResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> createAnimal(@RequestBody CreateAnimalRequest request, UriComponentsBuilder builder) {
        Animal animal = CreateAnimalRequest
                .dtoToEntityMapper(name -> speciesService.find(name).orElseThrow())
                .apply(request);
        animal = animalService.create(animal);
        return ResponseEntity.created(builder.pathSegment("api", "animals", "{id}")
                .buildAndExpand(animal.getId()).toUri()).build();
    }
    @CrossOrigin
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") long id) {
        Optional<Animal> animal = animalService.find(id);
        if (animal.isPresent()) {
            animalService.delete(animal.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    public ResponseEntity<Void> updateAnimal(@RequestBody UpdateAnimalRequest request, @PathVariable("id") long id) {
        Optional<Animal> animal = animalService.find(id);
        if (animal.isPresent()) {
            UpdateAnimalRequest.dtoToEntityUpdater().apply(animal.get(), request);
            animalService.update(animal.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
