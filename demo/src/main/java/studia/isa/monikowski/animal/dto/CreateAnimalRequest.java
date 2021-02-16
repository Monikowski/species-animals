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
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateAnimalRequest {

    private String name;

    private int age;

    private int body_mass;

    private int daily_food_mass;

    private String species;

    public static Function<CreateAnimalRequest, Animal> dtoToEntityMapper(
            Function<String, Species> speciesFunction) {
        return request -> Animal.builder()
                .name(request.getName())
                .age(request.getAge())
                .body_mass(request.getBody_mass())
                .daily_food_mass(request.getDaily_food_mass())
                .species(speciesFunction.apply(request.getSpecies()))
                .build();
    }
}
