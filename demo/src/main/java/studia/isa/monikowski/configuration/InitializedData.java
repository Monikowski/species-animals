package studia.isa.monikowski.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.service.AnimalService;
import studia.isa.monikowski.animal.service.SpeciesService;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.time.LocalDate;

@Component
public class InitializedData {

    private final AnimalService animalService;

    private final SpeciesService speciesService;

    @Autowired
    public InitializedData(AnimalService animalService, SpeciesService speciesService){
        this.animalService = animalService;
        this.speciesService = speciesService;
    }

    @PostConstruct
    private synchronized void init() {
        Species exampleSpecies = Species.builder()
                .species_name("golomb")
                .food_type("ludzki bol i cierpienie")
                .origin_location("pieklo")
                .average_age(1000)
                .build();

        speciesService.create(exampleSpecies);

        Species exampleSpecies2 = Species.builder()
                .species_name("pies")
                .food_type("chlep")
                .origin_location("buda")
                .average_age(17)
                .build();

        speciesService.create(exampleSpecies2);

        Animal exampleAnimal = Animal.builder()
                .age(9999)
                .body_mass(1234)
                .daily_food_mass(12345)
                .name("diablo")
                .species(exampleSpecies)
                .build();

        animalService.create(exampleAnimal);

        Animal exampleAnimal2 = Animal.builder()
                .age(1234)
                .body_mass(1)
                .daily_food_mass(1)
                .name("bomba")
                .species(exampleSpecies2)
                .build();

        animalService.create(exampleAnimal2);

        Animal exampleAnimal3 = Animal.builder()
                .age(5)
                .body_mass(12)
                .daily_food_mass(1)
                .name("salami")
                .species(exampleSpecies2)
                .build();

        animalService.create(exampleAnimal3);
    }
}
