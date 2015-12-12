package me.shreyasr.starfighter.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListenerExt;

import me.shreyasr.starfighter.network.CrossPlatformWebSocketListener;
import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.network.WebsocketSender;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener getApplicationListener() {
        final Websocket webSocketClient = new Websocket("ws://localhost:80");

        StarfighterGame game = new StarfighterGame(new WebsocketSender() {
            @Override
            public void send(String message) {
                webSocketClient.send(message);
            }
        });

        webSocketClient.addListener(new ForwardingWebSocketListener(game.listener));
        webSocketClient.open();

        return game;
    }

    static class ForwardingWebSocketListener implements WebsocketListenerExt {

        private CrossPlatformWebSocketListener listener;

        public ForwardingWebSocketListener(CrossPlatformWebSocketListener listener) {
            this.listener = listener;
        }

        @Override
        public void onOpen() {
            listener.onOpen();
        }

        @Override
        public void onClose() {
            listener.onClose(-1, "unknown", false);
        }

        @Override
        public void onMessage(String msg) {
            listener.onMessage(msg);
        }

        @Override
        public void onError() {
            listener.onError(new Exception("websocket error"));
        }
    }
}