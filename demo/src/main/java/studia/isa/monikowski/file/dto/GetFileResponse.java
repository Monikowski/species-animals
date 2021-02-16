package studia.isa.monikowski.file.dto;

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
import studia.isa.monikowski.file.entity.Details;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetFileResponse {

    private long id;

    private String some_text;

    private String email;

    private String file_name;


    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Details, GetFileResponse> entityToDtoMapper() {
        return details -> GetFileResponse.builder()
                .id(details.getId())
                .some_text(details.getSome_text())
                .email(details.getEmail())
                .file_name(details.getFile_name())
                .build();
    }
}
