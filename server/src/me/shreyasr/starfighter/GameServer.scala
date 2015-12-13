package me.shreyasr.starfighter

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

class GameServer extends WebSocketServer {

  override def onError(conn: WebSocket, ex: Exception): Unit = {
    println("error with " + conn.getRemoteSocketAddress  + ":" + ex)
  }

  override def onMessage(conn: WebSocket, message: String): Unit = {
    println("received message from " + conn.getRemoteSocketAddress + ": " + message)
    conn.send("Received: " + message)
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    println("closed " + conn.getRemoteSocketAddress + " with exit code " + code + " because: " + reason)
  }

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    println("opened " + conn.getRemoteSocketAddress)
  }
}
