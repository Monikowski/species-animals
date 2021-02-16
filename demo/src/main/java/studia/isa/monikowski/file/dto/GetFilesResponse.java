package studia.isa.monikowski.file.dto;


import lombok.*;
import studia.isa.monikowski.animal.dto.GetAnimalsResponse;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.file.entity.Details;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetFilesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Details {

        private Long id;

        private String email;

        private String some_text;

        private String file_name;
    }


    @Singular(value="details")
    private List<Details> detailss;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection< studia.isa.monikowski.file.entity.Details>, GetFilesResponse> entityToDtoMapper() {
        return details_all -> {
            GetFilesResponseBuilder response = GetFilesResponse.builder();
            details_all.stream()
                    .map(details -> Details.builder()
                            .id(details.getId())
                            .some_text(details.getSome_text())
                            .email(details.getEmail())
                            .file_name(details.getFile_name())
                            .build())
                    .forEach(response::details);
            return response.build();
        };

    }

}
