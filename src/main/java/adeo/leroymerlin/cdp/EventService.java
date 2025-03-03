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
        return eventRepository.findAllBy().stream()
                .map(event -> {
                    if (!event.getTitle().matches(".*\\[\\d+\\]$")) {
                        event.setTitle(event.getTitle() + " [" + event.getBands().size() + "]");
                    }
                    event.getBands().forEach(band -> {
                        if (!band.getName().matches(".*\\[\\d+\\]$")) {
                            band.setName(band.getName() + " [" + band.getMembers().size() + "]");
                        }
                    });
                    return event;
                })
                .filter(event -> event.getBands().stream()
                        .flatMap(band -> band.getMembers().stream())
                        .anyMatch(member -> member.getName().contains(query)))
                .toList();
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
