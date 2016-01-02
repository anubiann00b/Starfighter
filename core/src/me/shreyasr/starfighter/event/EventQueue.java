package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EventQueue {

    // oldest first
    private final List<Event> events = new LinkedList<Event>();
    private List<Event> newEvents;
    private final EventResolutionData data;

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

        if (events.contains(e)) {
            ReplacementEvent replacementEvent = new ReplacementEvent(e);
            events.add(replacementEvent);
        } else {
            events.add(e);
        }
    }

    private void forceAdd(Event e) {
        if (events.contains(e)) {
            events.remove(e);
        }
        events.add(e);
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
    private List<Event> eventsToAdd = new ArrayList<Event>();

    public void resolveEvents(double from, double to) {
        System.out.println("From=to : " + (to-from));
        double time = from;
        while (time <= to) {
            resolveEvents(time);
            time += 16;
        }
    }

    public void resolveEvents(double time) {
        Collections.sort(events);
        for (Iterator<Event> iter = events.iterator(); iter.hasNext(); ) {
            Event e = iter.next();
            if (eventsToCancel.contains(e.id)) {
                iter.remove();
                eventsToCancel.remove(e.id);
            } else if (e.startMillis <= time) {
                boolean keep = e.resolve(data, new EventQueueUpdater() {
                    @Override
                    public void addEvent(Event e) {
                        eventsToAdd.add(e);
                    }

                    @Override
                    public void cancelEvent(double id) {
                        if (id >= 0 && id <= 1) {
                            eventsToCancel.add(id);
                        }
                    }
                });
                if (!keep) iter.remove();
            }
        }
        for (Event e : eventsToAdd) {
            forceAdd(e);
        }
        eventsToAdd.clear();
    }

    public void removeAllEvents() {
        events.clear();
    }

    interface EventQueueUpdater {
        void addEvent(Event e);
        void cancelEvent(double id);
    }
}
