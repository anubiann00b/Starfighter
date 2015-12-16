package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.StarfighterGame;

public class PostRenderSystem extends EntitySystem {

    private final StarfighterGame game;

    public PostRenderSystem(int priority, StarfighterGame game) {
        super(priority);
        this.game = game;
    }

    public void update(float deltaTime) {
        if (game.shape.isDrawing()) game.shape.end();
        if (game.batch.isDrawing()) game.batch.end();
    }
}

