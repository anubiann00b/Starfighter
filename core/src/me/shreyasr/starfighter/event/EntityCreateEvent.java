package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;

public class EntityCreateEvent extends Event {

    private Entity entity;

    public EntityCreateEvent() { }

    public EntityCreateEvent(double startMillis, Entity entity) {
        super(startMillis);
        this.entity = entity;
    }

    @Override
    public boolean resolve(EventResolutionData data) {
        data.engine.addEntity(entity);
        return false;
    }
}
