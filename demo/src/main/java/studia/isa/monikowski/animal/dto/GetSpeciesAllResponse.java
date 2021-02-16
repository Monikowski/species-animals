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
import studia.isa.monikowski.animal.entity.Species;

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
public class GetSpeciesAllResponse {


    @Singular(value="species")
    private List<String> speciess;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<studia.isa.monikowski.animal.entity.Species>, GetSpeciesAllResponse> entityToDtoMapper() {
        return species_all -> {
            GetSpeciesAllResponseBuilder response = GetSpeciesAllResponse.builder();
            species_all.stream()
                    .map(Species::getSpecies_name)
                    .forEach(response::species);
            return response.build();
        };
    }

}
