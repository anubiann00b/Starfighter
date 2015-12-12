package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import me.shreyasr.starfighter.StarfighterGame;

public class SetProjectionMatrixSystem extends EntitySystem {

    private final StarfighterGame game;
    private final OrthographicCamera camera;

    public SetProjectionMatrixSystem(int priority, StarfighterGame game, OrthographicCamera camera) {
        super(priority);
        this.game = game;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        game.batch.setProjectionMatrix(camera.combined);
    }
}
