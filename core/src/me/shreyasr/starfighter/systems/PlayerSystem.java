package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

import me.shreyasr.starfighter.components.TypeComponent;

public abstract class PlayerSystem extends EntitySystem {

    private Entity player;

    public PlayerSystem(int priority) {
        super(priority);
    }

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(TypeComponent.MyPlayer.class).get()).first();
    }

    @Override
    public void update(float deltaTime) {
        updatePlayer(player, deltaTime);
    }

    public abstract void updatePlayer(Entity player, float deltaTime);
}
