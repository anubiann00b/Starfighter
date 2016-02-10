package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.network.GameState;

public class EventQueueUpdateSystem extends EntitySystem {

    private final EventQueue eventQueue;

    public EventQueueUpdateSystem(int priority, EventQueue eventQueue) {
        super(priority);
        this.eventQueue = eventQueue;
    }

    double time = 0;
    GameState gameState = null;

    @Override
    public void update(float deltaTime) {
        eventQueue.resolveEvents(System.currentTimeMillis());
//        time += deltaTime;
//        if (time > 3000 && gameState == null) {
//            List<Entity> entityList = new ArrayList<Entity>();
//            for (Entity e : getEngine().getEntities()) {
//                entityList.add(e);
//            }
//            gameState = (GameState)JsonSerializer.decode(JsonSerializer.encode(new GameState(
//                    System.currentTimeMillis(),
//                    entityList.toArray(new Entity[entityList.size()]),
//                    new Event[0])));
//        } else if (time > 5000) {
//            eventQueue.rollback(gameState);
//            time = 0;
//            gameState = null;
//        }
    }
}
