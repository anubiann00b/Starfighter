package me.shreyasr.starfighter.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.event.EntityCreateEvent;
import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.systems.CameraUpdateSystem;
import me.shreyasr.starfighter.systems.EventQueueNetworkPopulator;
import me.shreyasr.starfighter.systems.EventQueueNetworkSender;
import me.shreyasr.starfighter.systems.EventQueueUpdateSystem;
import me.shreyasr.starfighter.systems.MyShipInputUpdateSystem;
import me.shreyasr.starfighter.systems.ProjectileUpdateSystem;
import me.shreyasr.starfighter.systems.ShipGraphicsUpdateSystem;
import me.shreyasr.starfighter.systems.VelocityUpdateSystem;
import me.shreyasr.starfighter.systems.render.BackgroundRenderSystem;
import me.shreyasr.starfighter.systems.render.MainRenderSystem;
import me.shreyasr.starfighter.systems.render.PostRenderSystem;
import me.shreyasr.starfighter.systems.render.PreBatchRenderSystem;
import me.shreyasr.starfighter.systems.render.PreShapeRenderSystem;
import me.shreyasr.starfighter.systems.render.SetProjectionMatrixSystem;
import me.shreyasr.starfighter.util.EntityFactory;
import me.shreyasr.starfighter.util.JsonSerializer;

public class GameScreen extends ScreenAdapter {

    private StarfighterGame game;
    private Engine engine;
    private ExtendViewport viewport;
    private EventQueue eventQueue;
    private boolean initialized = false;

    private int sectorWidth = 4096;
    private int sectorHeight = 4096;

    public GameScreen(StarfighterGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        engine = new Engine();
        eventQueue = new EventQueue(engine);
        OrthographicCamera camera = new OrthographicCamera(640, 480);
        viewport = new ExtendViewport(800, 600, 1280, 720, camera);

        Entity player = EntityFactory.createPlayerShip();
        engine.addEntity(player);

        int priority = 0;
        // @formatter:off
        engine.addSystem(new MyShipInputUpdateSystem    (++priority, eventQueue));
        engine.addSystem(new ProjectileUpdateSystem     (++priority));
        engine.addSystem(new VelocityUpdateSystem       (++priority));
        engine.addSystem(new ShipGraphicsUpdateSystem   (++priority));
        engine.addSystem(new EventQueueNetworkSender    (++priority, game.webSocketSender, eventQueue));
        engine.addSystem(new EventQueueNetworkPopulator (++priority, game.webSocketListener, eventQueue));
        engine.addSystem(new EventQueueUpdateSystem     (++priority, eventQueue));

        engine.addSystem(new CameraUpdateSystem        (++priority, game, camera, viewport, sectorWidth, sectorHeight));
        engine.addSystem(new PreBatchRenderSystem      (++priority, game, camera));
        engine.addSystem(new    BackgroundRenderSystem (++priority, game));
        engine.addSystem(new SetProjectionMatrixSystem (++priority, game, camera));
        engine.addSystem(new    MainRenderSystem       (++priority, game));
        engine.addSystem(new PreShapeRenderSystem      (++priority, game, camera));
        engine.addSystem(new PostRenderSystem          (++priority, game));
        // @formatter:on

        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(engine.getSystem(MyShipInputUpdateSystem.class).input);

        game.webSocketSender.send(JsonSerializer.encode(new EntityCreateEvent(0, player)));

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
