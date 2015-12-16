package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EventQueue {

    // oldest first
    private final TreeSet<Event> events = new TreeSet<Event>();
    private List<Event> newEvents;
    private final EventResolutionData data;
    private EventListener eventListener;

    public EventQueue(Engine engine) {
        this(engine, true);
    }

    public EventQueue(Engine engine, boolean storeNewEvents) {
        data = new EventResolutionData(engine);
        if (storeNewEvents) {
            newEvents = new LinkedList<Event>();
        } else {
            newEvents = null;
        }
    }

    public void addEvent(Event e) {
        addEvent(e, false);
    }

    public void addEvent(Event e, boolean silent) {
        if (!silent && newEvents != null) newEvents.add(e);
        boolean added = events.add(e);
        if (!added) {
            events.remove(e);
            addEvent(e, true);
        }
    }

    public List<Event> popNewEvents() {
        if (newEvents == null) return null;
        List<Event> newEventsCopy = newEvents;
        newEvents = new LinkedList<Event>();
        return newEventsCopy;
    }

    public Event[] getAllEvents() {
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return events.toArray(new Event[0]);
    }

    private Set<Double> eventsToCancel = new HashSet<Double>();

    public void resolveEventsTo(long time) {
        for (Iterator<Event> iter = events.iterator(); iter.hasNext(); ) {
            Event e = iter.next();
            if (eventsToCancel.contains(e.id)) {
                iter.remove();
            } else if(e.startMillis <= time) {
                boolean keep = e.resolve(data, new EventCanceler() {
                    @Override
                    public void cancelEvent(double id) {
                        eventsToCancel.add(id);
                    }
                });
                if (!keep) iter.remove();
            }
        }
    }

    interface EventCanceler {
        void cancelEvent(double id);
    }

    interface EventListener {
        void newEvent(Event e);
    }
}
