package studia.isa.monikowski.animal.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name="species")
public class Species implements Serializable {

    @Id
    private String species_name;

    private String food_type;

    private String origin_location;

    private int average_age;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "species")
    @ToString.Exclude
    private List<Animal> animals;
}
