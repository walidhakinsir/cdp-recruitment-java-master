package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        return events;
    }


    /**
     * fix updateEvent method : get event by id to prevent brand and members data (like id,...) and update only nbStars and comment
     */
    public void updateEvent(Event event) {
        eventRepository.findById(event.getId())
                .ifPresent(eventToSave -> {
                    eventToSave.setNbStars(event.getNbStars());
                    eventToSave.setComment(event.getComment());
                    eventRepository.save(eventToSave);
                });

    }
}
