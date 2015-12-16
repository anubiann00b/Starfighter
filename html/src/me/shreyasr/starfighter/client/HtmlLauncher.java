package me.shreyasr.starfighter.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.core.client.GWT;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListenerExt;

import me.shreyasr.starfighter.StarfighterGame;
import me.shreyasr.starfighter.network.CrossPlatformWebSocketListener;
import me.shreyasr.starfighter.network.WebSocketSender;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener getApplicationListener() {
        final Websocket webSocketClient = new Websocket("ws://localhost:80");

        StarfighterGame game = new StarfighterGame(new WebSocketSender() {
            @Override
            public void send(String messageBase64) {
                try {
                    webSocketClient.send(messageBase64);
                } catch (Exception e) {
                    GWT.log(e.getMessage());
                }
            }
        });

        webSocketClient.addListener(new ForwardingWebSocketListener(game.webSocketListener));
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
            listener.onError(new Exception("unknown websocket error"));
        }
    }
}