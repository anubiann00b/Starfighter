package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EntityCreateEvent extends Event {

    private Entity entity;

    public EntityCreateEvent() { }

    public EntityCreateEvent(double startMillis, Entity entity) {
        super(startMillis, startMillis);
        this.entity = entity;
    }

    @Override
    public void resolve(EventResolutionData data, double timeMultiplier) {
        if (!data.engine.getEntities().contains(entity, false)) {
            data.engine.addEntity(entity);
        } else {
            System.out.println("Attempted to create existing entity.");
        }
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("en", entity);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        entity = json.readValue(Entity.class, jsonData.get("en"));
    }
}
