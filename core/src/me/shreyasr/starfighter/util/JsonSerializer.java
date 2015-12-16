package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;

import me.shreyasr.starfighter.event.Event;

public class JsonSerializer {

    private static final Json json = new Json();

    public static void init() {
        json.setSerializer(Entity.class, new EntitySerializer());
    }

    public static Event decode(String data) {
        return json.fromJson(Event.class, data);
    }

    public static String encode(Event event) {
        return json.toJson(event, Event.class);
    }
}
