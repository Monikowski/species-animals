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
public class CreateSpeciesRequest {

    private String species_name;

    private String food_type;

    private String origin_location;

    private int average_age;

    public static Function<CreateSpeciesRequest, Species> dtoToEntityMapper() {
        return request -> Species.builder()
                .species_name(request.getSpecies_name())
                .food_type(request.getFood_type())
                .origin_location(request.getOrigin_location())
                .average_age(request.getAverage_age())
                .build();
    }
}
