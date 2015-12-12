package me.shreyasr.starfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import me.shreyasr.starfighter.CrossPlatformWebSocketListener;
import me.shreyasr.starfighter.GameMain;
import me.shreyasr.starfighter.WebsocketSender;

public class DesktopLauncher {
	public static void main(String[] arg) throws URISyntaxException {
		final ForwardingWebSocketClient webSocketClient
				= new ForwardingWebSocketClient(new URI("ws://localhost:80"));

		GameMain game = new GameMain(new WebsocketSender() {
			@Override
			public void send(String message) {
				webSocketClient.send(message);
			}
		});

		webSocketClient.setListener(game.listener);
		webSocketClient.connect();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(game, config);
	}

	static class ForwardingWebSocketClient extends WebSocketClient {

		private CrossPlatformWebSocketListener listener;

		public void setListener(CrossPlatformWebSocketListener listener) {
			this.listener = listener;
		}

		public ForwardingWebSocketClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			listener.onOpen();
		}

		@Override
		public void onMessage(String message) {
			listener.onMessage(message);
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
			listener.onClose(code, reason, remote);
		}

		@Override
		public void onError(Exception ex) {
			listener.onError(ex);
		}
	}
}
