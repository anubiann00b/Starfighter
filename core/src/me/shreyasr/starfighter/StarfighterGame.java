package me.shreyasr.starfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.starfighter.network.AccumulatingWebSocketListener;
import me.shreyasr.starfighter.network.WebSocketSender;
import me.shreyasr.starfighter.screens.LoadingScreen;
import me.shreyasr.starfighter.util.Assets;
import me.shreyasr.starfighter.util.JsonSerializer;

public class StarfighterGame extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shape;
    public AssetManager assetManager;
    public BitmapFont font;

	public final AccumulatingWebSocketListener webSocketListener;
	public final WebSocketSender webSocketSender;

	public StarfighterGame(WebSocketSender webSocketSender) {
		this.webSocketSender = webSocketSender;
		this.webSocketListener = new AccumulatingWebSocketListener();
	}
	
	@Override
	public void create() {
        JsonSerializer.init();
        assetManager = new AssetManager();
        Assets.loadAll(assetManager);
		batch = new SpriteBatch();
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        assetManager.finishLoading();

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        this.setScreen(new LoadingScreen(this));
	}
}
