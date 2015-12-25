package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.VelComponent;

public class TurnEvent extends Event {

    private double targetId;
    public double accel;
    private double cancelId;

    public TurnEvent() { }

    public TurnEvent(double startMillis, double targetId, double accel) {
        this(startMillis, targetId, accel, -1);
    }

    public TurnEvent(double startMillis, double targetId, double accel, double cancelId) {
        super(startMillis);
        this.targetId = targetId;
        this.accel = accel;
        this.cancelId = cancelId;
    }

    @Override
    public boolean resolve(EventResolutionData data, EventQueue.EventQueueUpdater queueUpdater) {
        queueUpdater.cancelEvent(cancelId);
        Entity target = data.getEntityById(targetId);

        if (target != null) target.getComponent(VelComponent.class).dir += accel;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " accel: " + accel;
    }
}