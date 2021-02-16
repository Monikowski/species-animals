package studia.isa.monikowski.animal.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.animal.entity.Species;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAnimalResponse {

    private long id;

    private String name;

    private int age;

    private int body_mass;

    private int daily_food_mass;

    private String species;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Animal, GetAnimalResponse> entityToDtoMapper() {
        return animal -> GetAnimalResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .age(animal.getAge())
                .body_mass(animal.getBody_mass())
                .daily_food_mass(animal.getDaily_food_mass())
                .species(animal.getSpecies().getSpecies_name())
                .build();
    }
}
