package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.DirComponent;

public class TurnEvent extends Event {

    private long targetId;
    public double acceleration;

    protected TurnEvent() { }

    public TurnEvent(long startMillis, long targetId, double acceleration) {
        super(startMillis);
        this.targetId = targetId;
        this.acceleration = acceleration;
    }

    @Override
    public boolean resolve(EventResolutionData data) {
        Entity target = data.getEntityById(targetId);

        if (target != null) target.getComponent(DirComponent.class).dir += acceleration;
        return true;
    }
}