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

    public boolean resolve(EventResolutionData data, EventQueue.EventQueueUpdater queueUpdater) {
        return resolve(data);
    }

    @Override
    public int compareTo(Event o) {
        if (this.equals(o)) return 0;
        if (o == null) return -1;
        if (startMillis == o.startMillis) return Double.compare(id, o.id);
        return -Double.compare(startMillis, o.startMillis);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Event && ((Event)obj).id == id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + ((long)startMillis)%10000 + " " + (int)(id*10000)%10000;
    }
}
