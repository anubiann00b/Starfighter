package me.shreyasr.starfighter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import me.shreyasr.starfighter.StarfighterGame;

public class LoadingScreen extends ScreenAdapter {

    private StarfighterGame game;

    private boolean ready = true;

    public LoadingScreen(StarfighterGame game) {
        this.game = game;
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (ready) game.setScreen(new GameScreen(game));
    }
}
