package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class EventQueue {

    // oldest first
    private final TreeSet<Event> events = new TreeSet<>();
    private final EventResolutionData data;

    public EventQueue(Engine engine) {
        data = new EventResolutionData(engine);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    private Set<Long> eventsToCancel = new HashSet<>();

    public void resolveEventsTo(long time) {
        for (Iterator<Event> iter = events.descendingIterator(); iter.hasNext(); ) {
            Event e = iter.next();
            if (eventsToCancel.contains(e.id)) {
                iter.remove();
            } else if(e.startMillis <= time) {
                boolean keep = e.resolve(data, new EventCanceler() {
                    @Override
                    public void cancelEvent(long id) {
                        eventsToCancel.add(id);
                    }
                });
                if (!keep) iter.remove();
            }
        }
    }

    interface EventCanceler {
        void cancelEvent(long id);
    }
}
