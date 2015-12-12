package me.shreyasr.starfighter.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.systems.CameraUpdateSystem;
import me.shreyasr.starfighter.systems.ShipGraphicsUpdateSystem;
import me.shreyasr.starfighter.systems.render.MainRenderSystem;
import me.shreyasr.starfighter.systems.render.PreBatchRenderSystem;
import me.shreyasr.starfighter.systems.render.PreShapeRenderSystem;
import me.shreyasr.starfighter.util.EntityFactory;
import me.shreyasr.starfighter.util.Log;

public class GameScreen extends ScreenAdapter {

    private StarfighterGame game;

    public Engine engine;

    private InputMultiplexer inputMultiplexer;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private boolean initialized = false;

    public GameScreen(StarfighterGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        engine = new Engine();
        camera = new OrthographicCamera(640, 480);
        viewport = new ExtendViewport(800, 600, 1280, 720, camera);

        engine.addEntity(EntityFactory.createPlayerShip());

        int priority = 0;
        // @formatter:off
        engine.addSystem(new ShipGraphicsUpdateSystem (++priority));

        engine.addSystem(new CameraUpdateSystem   (++priority, game, camera, viewport));
        engine.addSystem(new PreBatchRenderSystem (++priority, game, camera));
        engine.addSystem(new    MainRenderSystem  (++priority, game));
        engine.addSystem(new PreShapeRenderSystem (++priority, game, camera));
        engine.addSystem(new PostRenderSystem     (++priority, game));
        // @formatter:on

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        initialized = true;
    }

    @Override
    public void render(float delta) {
        if (!initialized) return;
        engine.update(Gdx.graphics.getRawDeltaTime() * 1000);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
