package me.shreyasr.starfighter.event;

import java.util.Random;

public class Event implements Comparable<Event> {

    private static Random random = new Random();

    public double startMillis;
    public double id;

    public Event() { } // For serialization

    public Event(double startMillis) {
        this.startMillis = startMillis;
        id = random.nextDouble();
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
        if (o == null) return -1;
        return -Double.compare(startMillis, o.startMillis);
    }
}
