package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import me.shreyasr.starfighter.StarfighterGame;

public class PreBatchRenderSystem extends EntitySystem {

    private final StarfighterGame game;
    private OrthographicCamera camera;

    public PreBatchRenderSystem(int priority, StarfighterGame game, OrthographicCamera camera) {
        super(priority);
        this.game = game;
        this.camera = camera;
    }

    public void update(float deltaTime) {
        if (game.shape.isDrawing()) game.shape.end();
        if (game.batch.isDrawing()) game.batch.end();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
    }
}
