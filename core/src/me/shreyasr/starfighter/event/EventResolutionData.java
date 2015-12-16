package me.shreyasr.starfighter.event;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import me.shreyasr.starfighter.components.IdComponent;

public class EventResolutionData {

    public Engine engine;
    private ImmutableArray<Entity> idEntities;

    public EventResolutionData(Engine engine) {
        this.engine = engine;
        idEntities = engine.getEntitiesFor(Family.all(IdComponent.class).get());
    }

    public Entity getEntityById(double id) {
        for (Entity e : idEntities) {
            if (e.getComponent(IdComponent.class).id == id) {
                return e;
            }
        }
        return null;
    }
}
