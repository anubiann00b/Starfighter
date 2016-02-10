package me.shreyasr.starfighter.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class DisableEvent extends Event {

    private double disableId;

    public DisableEvent() { }

    public DisableEvent(double time, double disableId) {
        super(time, time);
        this.disableId = disableId;
    }

    @Override
    public void resolve(EventResolutionData data, double timeMultiplier,
                        EventQueue.EventQueueUpdater queueUpdater) {
        queueUpdater.cancelEvent(disableId, startMillis);
    }

    @Override
    public String toString() {
        return super.toString() + " " + disableId;
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("di", disableId);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        disableId = jsonData.get("di").asDouble();
    }
}
