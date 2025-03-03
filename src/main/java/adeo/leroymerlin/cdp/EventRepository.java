package adeo.leroymerlin.cdp;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EventRepository extends Repository<Event, Long> {

    /**
     * add @Transactional to persist deleteById method
     * @Transactional(readOnly = true) in EventRepository class will not perform any write operations
     */
    @Transactional
    void deleteById(Long eventId);

    List<Event> findAllBy();

    /**
     * add save method to persist event
     */
    @Transactional
    Event save(Event event);

    /**
     * add findById method to get event by id
     */
    Optional<Event> findById(Long eventId);
}
