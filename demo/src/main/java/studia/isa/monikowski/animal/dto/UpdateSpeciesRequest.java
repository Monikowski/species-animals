package studia.isa.monikowski.animal.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.sql.Update;
import studia.isa.monikowski.animal.entity.Species;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateSpeciesRequest {

    private String food_type;

    private String origin_location;

    private int average_age;
    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<Species, UpdateSpeciesRequest, Species> dtoToEntityUpdater() {
        return (species, request) -> {
            species.setFood_type(request.getFood_type());
            species.setOrigin_location(request.getOrigin_location());
            species.setAverage_age(request.getAverage_age());
            return species;
        };
    }
}