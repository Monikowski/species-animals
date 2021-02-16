package studia.isa.monikowski.animal.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET characters response. Contains list of available characters. Can be used to list particular user's characters as
 * well as all characters in the game.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAnimalsResponse {

    /**
     * Represents single character in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Animal {

        private Long id;

        private String name;

        private String species;

    }

    @Singular
    private List<Animal> animals;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<studia.isa.monikowski.animal.entity.Animal>, GetAnimalsResponse> entityToDtoMapper() {
        return animals -> {
            GetAnimalsResponseBuilder response = GetAnimalsResponse.builder();
            animals.stream()
                    .map(animal -> Animal.builder()
                            .id(animal.getId())
                            .name(animal.getName())
                            .species(animal.getSpecies().getSpecies_name())
                            .build())
                    .forEach(response::animal);
            return response.build();
        };
    }

}
