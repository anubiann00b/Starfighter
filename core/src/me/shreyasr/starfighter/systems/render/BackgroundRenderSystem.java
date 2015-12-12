package me.shreyasr.starfighter.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Texture;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.util.Assets;

public class BackgroundRenderSystem extends EntitySystem {

    private final StarfighterGame game;

    public BackgroundRenderSystem(int priority, StarfighterGame game) {
        super(priority);
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        Texture space = game.assetManager.get(Assets.SPACE.getFile(), Texture.class);
        game.batch.draw(space,    0,    0, 2048, 2048);
        game.batch.draw(space, 2048,    0, 2048, 2048);
        game.batch.draw(space,    0, 2048, 2048, 2048);
        game.batch.draw(space, 2048, 2048, 2048, 2048);
    }
}
