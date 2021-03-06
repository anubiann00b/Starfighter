package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import me.shreyasr.starfighter.StarfighterGame;

public class PreShapeRenderSystem extends EntitySystem {

    private final StarfighterGame game;
    private final OrthographicCamera camera;

    public PreShapeRenderSystem(int priority, StarfighterGame game, OrthographicCamera camera) {
        super(priority);
        this.game = game;
        this.camera = camera;
    }

    public void update(float deltaTime) {
        if (game.shape.isDrawing()) game.shape.end();
        if (game.batch.isDrawing()) game.batch.end();

        game.shape.setProjectionMatrix(camera.combined);
        game.shape.begin();
    }
}
