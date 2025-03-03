package adeo.leroymerlin.cdp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    private EventController eventController;

    @Mock
    private EventService eventService;

    @Before
    public void setup() {
        eventController = new EventController(eventService);
    }

    @Test
    public void shouldFindEvents() {
        Event event1 = new Event();
        event1.setTitle("Event 1");
        Event event2 = new Event();
        event2.setTitle("Event 2");
        List<Event> events = Arrays.asList(event1, event2);

        when(eventService.getEvents()).thenReturn(events);

        List<Event> response = eventController.findEvents();
        assertEquals(events, response);
    }

    @Test
    public void shouldFindFilteredEvents() {
        String query = "Memo";
        Event event1 = new Event();
        event1.setTitle("Event 1");
        List<Event> filteredEvents = Arrays.asList(event1);

        when(eventService.getFilteredEvents(query)).thenReturn(filteredEvents);

        List<Event> response = eventController.findEvents(query);
        assertEquals(filteredEvents, response);
    }

    @Test
    public void shouldDeleteEvent() {
        Long eventId = 1L;
        doNothing().when(eventService).delete(eventId);

        eventController.deleteEvent(eventId);
        verify(eventService, times(1)).delete(eventId);
    }

    @Test
    public void shouldUpdateEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setNbStars(5);
        event.setComment("Great event!");

        doNothing().when(eventService).updateEvent(event);

        eventController.updateEvent(eventId, event);
        verify(eventService, times(1)).updateEvent(event);
    }

}
