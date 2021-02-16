package studia.isa.monikowski.animal.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import studia.isa.monikowski.animal.entity.Species;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetSpeciesResponse {

    private String species_name;

    private String food_type;

    private String origin_location;

    private int average_age;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Species, GetSpeciesResponse> entityToDtoMapper() {
        return species -> GetSpeciesResponse.builder()
                .species_name(species.getSpecies_name())
                .food_type(species.getFood_type())
                .origin_location(species.getOrigin_location())
                .average_age(species.getAverage_age())
                .build();
    }
}
