package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.starfighter.components.TypeComponent;

public class EntitySerializer extends Serializer<Entity> {

    @Override
    public void write(Kryo kryo, Output output, Entity entity) {
        Component[] components = entity.getComponents().toArray(Component.class);
        List<Component> newComponents = new ArrayList<Component>();
        for (Component c : components) {
            if (c instanceof TypeComponent.MyPlayer) continue;
            newComponents.add(c);
        }
        kryo.writeObject(output, newComponents.toArray(new Component[newComponents.size()]));
    }

    @Override
    public Entity read(Kryo kryo, Input input, Class<Entity> type) {
        Component[] components = kryo.readObject(input, Component[].class);
        Entity e = new Entity();
        for (Component c : components) {
            e.add(c);
        }
        return e;
    }
}
