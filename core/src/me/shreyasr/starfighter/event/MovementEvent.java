package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.VelComponent;

public class MovementEvent extends Event {

    private long targetId;
    public double acceleration;

    protected MovementEvent() { }

    public MovementEvent(long startMillis, long targetId, double acceleration) {
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
