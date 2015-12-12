package me.shreyasr.starfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.starfighter.network.CrossPlatformWebSocketListener;
import me.shreyasr.starfighter.network.WebSocketListenerImpl;
import me.shreyasr.starfighter.network.WebsocketSender;
import me.shreyasr.starfighter.screens.LoadingScreen;
import me.shreyasr.starfighter.util.Assets;

public class StarfighterGame extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shape;
    public AssetManager assetManager;
    public BitmapFont font;

	public final CrossPlatformWebSocketListener listener;
	private WebsocketSender websocketSender;

	public StarfighterGame(WebsocketSender websocketSender) {
		this.websocketSender = websocketSender;
		this.listener = new WebSocketListenerImpl();
	}
	
	@Override
	public void create() {
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
