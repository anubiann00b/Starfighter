package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import me.shreyasr.starfighter.components.VelComponent;

public class MovementEvent extends Event {

    private double targetId;
    public double accel;

    public MovementEvent() { }

    public MovementEvent(double startMillis, double targetId, double accel) {
        super(startMillis, Double.MAX_VALUE);
        this.targetId = targetId;
        this.accel = accel;
    }

    @Override
    public void resolve(EventResolutionData data, double timeMultiplier) {
        Entity target = data.getEntityById(targetId);
        if (target != null) target.getComponent(VelComponent.class).speed += accel*timeMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() + " accel: " + accel;
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("ti", targetId);
        json.writeValue("ac", accel);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        targetId = jsonData.get("ti").asDouble();
        accel = jsonData.get("ac").asDouble();
    }
}
