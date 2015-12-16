package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.starfighter.components.TypeComponent;

public class EntitySerializer implements Json.Serializer<Entity> {

    @Override
    public void write(Json json, Entity entity, Class knownType) {
        ImmutableArray<Component> componentsList = entity.getComponents();
        Component[] components = new Component[componentsList.size()];
        for (int i = 0; i < componentsList.size(); i++) {
            components[i] = componentsList.get(i);
        }
        List<Component> newComponents = new ArrayList<Component>();
        for (Component c : components) {
            if (c instanceof TypeComponent.MyPlayer) continue;
            newComponents.add(c);
        }
        Component[] finalComponents = new Component[newComponents.size()];
        for (int i = 0; i < newComponents.size(); i++) {
            finalComponents[i] = newComponents.get(i);
        }
        json.writeValue(finalComponents);
    }

    @Override
    public Entity read(Json json, JsonValue jsonData, Class type) {
        Component[] components = json.readValue(Component[].class, jsonData);
        Entity e = new Entity();
        for (Component c : components) {
            e.add(c);
        }
        return e;
    }
}
