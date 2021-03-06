package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.TypeComponent;
import me.shreyasr.starfighter.util.MathHelper;

public class CameraUpdateSystem extends EntitySystem {

    private final StarfighterGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final int worldWidth;
    private final int worldHeight;
    private Entity player;

    public CameraUpdateSystem(int priority, StarfighterGame game,
                              OrthographicCamera camera, Viewport viewport,
                              int worldWidth, int worldHeight) {
        super(priority);
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(TypeComponent.MyPlayer.class).get()).first();
    }

    @Override
    public void update(float deltaTime) {
        PosComponent pos = player.getComponent(PosComponent.class);
        camera.position.set(
                MathHelper.clamp(viewport.getWorldWidth()/2, pos.x,
                        worldWidth-viewport.getWorldWidth() /2),
                MathHelper.clamp(viewport.getWorldHeight()/2, pos.y,
                        worldHeight-viewport.getWorldHeight()/2),
                0);
        viewport.apply();
        camera.update();
    }
}
