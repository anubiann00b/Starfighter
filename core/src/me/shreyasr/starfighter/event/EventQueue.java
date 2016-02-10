package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.network.GameState;

public class EventQueue {

    // oldest first
    private final List<Event> events = new LinkedList<Event>();
    private List<Event> newEvents;
    private final EventResolutionData data;
    private double lastTime = 0;

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
        if (newEvents != null) newEvents.add(e);
        addSilent(e);
    }

    public void addSilent(Event newEvent) {
        for (Event oldEvent : events) {
            if (oldEvent.id == newEvent.id) {
                System.out.println("EXISTING EVENT ADDED");
                break;
            }
        }
        if (newEvent.startMillis < lastTime) {
            lastTime = newEvent.startMillis;
        }
        events.add(newEvent);
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

    public void rollback(GameState gameState) {
        for (Entity newEntity : gameState.entities) {
            for (Entity oldEntity : data.engine.getEntities()) {
                IdComponent oldId = oldEntity.getComponent(IdComponent.class);
                IdComponent newId = newEntity.getComponent(IdComponent.class);
                if (oldId.equals(newId)) {
                    for (Component c : newEntity.getComponents()) {
                        if (c instanceof IdComponent) continue;
                        oldEntity.add(c);
                    }
                } else {
                    data.engine.addEntity(newEntity);
                }
            }
        }

        // merge gameState sent over and existing events
        for (Event newEvent : gameState.events) {
            boolean found = false;
            for (Event currentEvent : events) {
                if (newEvent.id == currentEvent.id) found = true;
            }
            if (!found) addSilent(newEvent);
        }

        setTime(gameState.time);
    }

    public void resolveEvents(double to) {
        while (lastTime < to) {
            double newTime = (lastTime+20 <= to) ?
                    lastTime + 20 : to;
            resolveEventsAt(lastTime, newTime);
            lastTime = newTime;
        }
    }

    private double firstTime = -1;

    public void setTime(double time) {
        if (firstTime == -1) {
            firstTime = time;
        }
        if (time < lastTime || lastTime == 0) {
            lastTime = time;
        }
    }

    private List<Event> eventsToAdd = new ArrayList<Event>();

    // from inclusive, to exclusive
    private void resolveEventsAt(final double from, final double to) {
        Collections.sort(events);
//        System.out.println(from%10000 + " " + to%10000);
        for (final Event e : events) {
            double eventStart = e.startMillis;
            double eventEnd = Math.min(e.endMillis, e.disableMillis);

            double delta = Math.min(eventEnd, to) - Math.max(eventStart, from);
            if (delta < 0) continue;
            e.resolve(data, delta/(1000.0/60.0), new EventQueueUpdater() {
                @Override
                public void addEvent(Event e) {
                    eventsToAdd.add(e);
                }

                @Override
                public void cancelEvent(double id, double time) {
                    for (Event e : events) {
                        if (e.id == id) e.disableMillis = time;
                    }
                }
            });
        }
        for (Event e : eventsToAdd) {
            addSilent(e);
        }
        eventsToAdd.clear();
    }

    public String getGraph(double start, double end) {
        StringBuilder builder = new StringBuilder();
        int width = 50;
        double duration = end - start;
        Collections.sort(events);
        for (Event e : events) {
            char[] chars = new char[width];
            Arrays.fill(chars, '-');
            int startPos = (int)((e.startMillis - start)/duration*width);
            int endPos = (int)((e.endMillis - start)/duration*width);
            int disabledPos = (int)((e.disableMillis - start)/duration*width);
            if (endPos >= 0 && endPos < width) {
                chars[endPos] = 'E';
            }
            if (disabledPos >= 0 && disabledPos < width) {
                chars[disabledPos] = 'D';
            }
            if (startPos >= 0 && startPos < width) {
                chars[startPos] = 'S';
            }
            builder.append(chars).append(e.getClass().getSimpleName()).append('\n');
        }
        return builder.toString();
    }

    public void removeAllEvents() {
        events.clear();
    }

    interface EventQueueUpdater {
        void addEvent(Event e);
        void cancelEvent(double id, double time);
    }
}
