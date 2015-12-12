package me.shreyasr.starfighter.network;

public interface CrossPlatformWebSocketListener {
    void onOpen();
    void onClose(int code, String reason, boolean remote);
    void onMessage(String message);
    void onError(Exception ex);
}

