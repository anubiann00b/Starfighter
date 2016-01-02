package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;

import me.shreyasr.starfighter.event.Event;
import me.shreyasr.starfighter.network.GameState;

public class JsonSerializer {

    private static final Json json = new Json();

    public static void init() {
        init(new EntitySerializer());
    }

    public static void init(Json.Serializer<Entity> entitySerializer) {
        json.setSerializer(Entity.class, entitySerializer);
    }

    public static Object decode(String data) {
        return json.fromJson(Object.class, data);
    }

    public static String encode(Event event) {
        return json.toJson(event, Object.class);
    }

    public static String encode(GameState gameState) {
        return json.toJson(gameState, Object.class);
    }
}
