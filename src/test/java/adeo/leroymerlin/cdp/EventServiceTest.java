package adeo.leroymerlin.cdp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepository;


    @Before
    public void setup() {
        eventService = new EventService(eventRepository);
    }

    @Test
    public void shouldGetEvents() {
        Event event1 = new Event();
        event1.setTitle("Event 1");
        Event event2 = new Event();
        event2.setTitle("Event 2");
        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.findAllBy()).thenReturn(events);

        List<Event> result = eventService.getEvents();
        assertEquals(events, result);
    }

    @Test
    public void shouldDeleteEvent() {
        Long eventId = 1L;
        doNothing().when(eventRepository).deleteById(eventId);

        eventService.delete(eventId);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    public void shouldGetFilteredEvents() {
        String query = "Member";
        Event event1 = new Event();
        event1.setTitle("Event 1");
        Band band1 = new Band();
        band1.setName("Band 1");
        Member member1 = new Member();
        member1.setName("Member 1");
        band1.setMembers(Set.of(member1));
        event1.setBands(Set.of(band1));
        List<Event> events = Arrays.asList(event1);

        when(eventRepository.findAllBy()).thenReturn(events);

        List<Event> result = eventService.getFilteredEvents(query);
        assertEquals(events, result);
    }

    @Test
    public void shouldUpdateEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setNbStars(5);
        event.setComment("Great event!");

        Event existingEvent = new Event();
        existingEvent.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        eventService.updateEvent(event);

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).save(existingEvent);
        assertEquals(5, existingEvent.getNbStars().intValue());
        assertEquals("Great event!", existingEvent.getComment());
    }
}
