package me.shreyasr.starfighter.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Random;

public class Event implements Comparable<Event>, Json.Serializable {

    private static Random random = new Random();

    public double id;
    public double startMillis;
    public double endMillis;
    public double disableMillis;
    public boolean hasRunOnce = false;

    public Event() { } // For serialization

    public Event(double startMillis, double endMillis) {
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.disableMillis = endMillis;
        id = random.nextDouble();
    }

    public void resolve(EventResolutionData data, double timeMultiplier) {

    }

    public void resolve(EventResolutionData data, double timeMultiplier,
                        EventQueue.EventQueueUpdater queueUpdater) {
        resolve(data, timeMultiplier);
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
        return this.getClass().getSimpleName() + ": "
                + ((long)startMillis)%10000 + " "
                + (int)(id*10000)%10000;
    }

    @Override
    public void write(Json json) {
        json.writeValue("id", id);
        json.writeValue("sm", startMillis);
        json.writeValue("em", endMillis);
        json.writeValue("ds", disableMillis);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = jsonData.get("id").asDouble();
        startMillis = jsonData.get("sm").asDouble();
        endMillis = jsonData.get("em").asDouble();
        disableMillis = jsonData.get("ds").asDouble();
        hasRunOnce = false;
    }
}
