package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

import me.shreyasr.starfighter.components.IdComponent;

public class EngineUtils {

    public static Entity getById(Engine engine, IdComponent id) {
        for (Entity e : engine.getEntitiesFor(Family.all(IdComponent.class).get())) {
            if (id.equals(e.getComponent(IdComponent.class))) {
                return e;
            }
        }
        return null;
    }
}
