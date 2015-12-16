package me.shreyasr.starfighter.network;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.shreyasr.starfighter.util.Log;

public class AccumulatingWebSocketListener implements CrossPlatformWebSocketListener {

    private Queue<String> messages = new ConcurrentLinkedQueue<String>();

    public String popMessage() {
        return messages.poll();
    }

    @Override
    public void onOpen() {
        Log.i("websocket opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("websocket closed: " + code + ": " + reason);
    }

    @Override
    public void onMessage(String message) {
//        Log.i("websocket recv: " + message);
        messages.add(message);
    }

    @Override
    public void onError(Exception ex) {
        Log.e(ex);
    }
}
