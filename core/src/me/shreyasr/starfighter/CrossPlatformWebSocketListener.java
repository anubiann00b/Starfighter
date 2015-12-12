package me.shreyasr.starfighter;

public interface CrossPlatformWebSocketListener {
    void onOpen();
    void onClose(int code, String reason, boolean remote);
    void onMessage(String message);
    void onError(Exception ex);
}

class WebSocketListenerImpl implements CrossPlatformWebSocketListener {

    @Override
    public void onOpen() {
        Log.i("websocket opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("websocket closed: " + code + ": " + reason);
    }

    @Override
    public void onMessage(String message) {
        Log.i("websocket recv: " + message);
    }

    @Override
    public void onError(Exception ex) {
        Log.e("websocket error: " + ex);
    }
}

