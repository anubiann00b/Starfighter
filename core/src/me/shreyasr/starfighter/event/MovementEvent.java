package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.VelComponent;

public class MovementEvent extends Event {

    private double targetId;
    public double acceleration;

    public MovementEvent() { }

    public MovementEvent(double startMillis, double targetId, double acceleration) {
        super(startMillis);
        this.targetId = targetId;
        this.acceleration = acceleration;
    }

    @Override
    public boolean resolve(EventResolutionData data) {
        Entity target = data.getEntityById(targetId);

        if (target != null) target.getComponent(VelComponent.class).velocity += acceleration;
        return true;
    }
}
