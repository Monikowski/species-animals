package studia.isa.monikowski.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing data from underlying data stores. The interface does not define how the data
 * is retrieved. The interface defines only basic methods which are not aware of entity object structure. Additional
 * methods (like find by or order should be defined as methods in implementing class.
 *
 * @param <E> type of the entity
 * @param <K> type of the primary key
 */
public interface Repository<E, K> {

    /**
     * Find entity object using its primary key.
     *
     * @param id object primary key
     * @return container (can be empty) with entity object
     */
    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void delete(E entity);


    //void update(E entity);

}