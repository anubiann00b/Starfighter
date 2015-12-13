package me.shreyasr.starfighter.event;

import java.security.SecureRandom;

public abstract class Event implements Comparable<Event> {

    private static SecureRandom random = new SecureRandom();

    public long startMillis;
    public long id;

    public Event(long startMillis) {
        this.startMillis = startMillis;
        id = random.nextLong();
    }

    // return true to keep in queue
    public boolean resolve(EventResolutionData data) {
        return false;
    }

    public boolean resolve(EventResolutionData data, EventQueue.EventCanceler eventCanceler) {
        return resolve(data);
    }

    @Override
    public int compareTo(Event o) {
        if (o == null) return 1;
        return Long.compare(startMillis, o.startMillis);
    }
}
