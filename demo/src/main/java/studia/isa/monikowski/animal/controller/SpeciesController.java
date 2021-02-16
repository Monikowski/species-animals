package studia.isa.monikowski.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import studia.isa.monikowski.animal.dto.*;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.service.SpeciesService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/species_all")
public class SpeciesController {
    private SpeciesService speciesService;

    @Autowired
    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<GetSpeciesAllResponse> getSpeciesAll() {
        List<Species> all = speciesService.findAll();
        Function<Collection<Species>, GetSpeciesAllResponse> mapper = GetSpeciesAllResponse.entityToDtoMapper();
        GetSpeciesAllResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<GetSpeciesResponse> getSpecies(@PathVariable("id") String name) {
        return speciesService.find(name)
                .map(value -> ResponseEntity.ok(GetSpeciesResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> createAnimal(@RequestBody CreateSpeciesRequest request, UriComponentsBuilder builder) {
        Species species = CreateSpeciesRequest
                .dtoToEntityMapper()
                .apply(request);
        speciesService.create(species);
        return ResponseEntity.created(builder.pathSegment("api", "species", "{id}")
                .buildAndExpand(species.getSpecies_name()).toUri()).build();
    }
    @CrossOrigin
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable("id") String name) {
        Optional<Species> species = speciesService.find(name);
        if (species.isPresent()) {
            speciesService.delete(species.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PutMapping("{id}")
    public ResponseEntity<Void> updateSpecies(@RequestBody UpdateSpeciesRequest request, @PathVariable("id") String name) {
        Optional<Species> species = speciesService.find(name);
        if (species.isPresent()) {
            UpdateSpeciesRequest.dtoToEntityUpdater().apply(species.get(), request);
            speciesService.update(species.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
