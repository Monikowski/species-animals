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
import studia.isa.monikowski.animal.entity.Animal;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateAnimalRequest {

    private String name;

    private int age;

    private int body_mass;

    private int daily_food_mass;
    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<Animal, UpdateAnimalRequest, Animal> dtoToEntityUpdater() {
        return (animal, request) -> {
            animal.setName(request.getName());
            animal.setAge(request.getAge());
            animal.setBody_mass(request.getBody_mass());
            animal.setDaily_food_mass(request.getDaily_food_mass());
            return animal;
        };
    }
}