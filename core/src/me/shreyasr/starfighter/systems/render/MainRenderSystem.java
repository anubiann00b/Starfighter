package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.TextureComponent;
import me.shreyasr.starfighter.components.TextureTransformComponent;

public class MainRenderSystem extends IteratingSystem {

    private final StarfighterGame game;

    public MainRenderSystem(int priority, StarfighterGame game) {
        super(
                Family.all(PosComponent.class,
                        TextureComponent.class,
                        TextureTransformComponent.class)
                        .get(),
                priority);
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PosComponent pos = entity.getComponent(PosComponent.class);
        TextureTransformComponent ttc = entity.getComponent(TextureTransformComponent.class);

        if (ttc.hide) return;

        Texture texture = game.assetManager.get(entity.getComponent(TextureComponent.class).file, Texture.class);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        game.batch.setColor(ttc.color);

        game.batch.draw(texture, pos.x - ttc.originX, pos.y - ttc.originY, ttc.originX, ttc.originY,
                ttc.screenWidth, ttc.screenHeight, 1, 1, ttc.rotation,
                ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, false, false);
    }
}

