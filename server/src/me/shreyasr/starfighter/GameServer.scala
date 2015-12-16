package me.shreyasr.starfighter

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.utils.Base64Coder
import me.shreyasr.starfighter.event.{EntityCreateEvent, Event, EventQueue}
import me.shreyasr.starfighter.systems.{VelocityUpdateSystem, EventQueueUpdateSystem}
import me.shreyasr.starfighter.util.KryoManager
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

import scala.collection.JavaConversions._

class GameServer extends WebSocketServer {

  val engine = new Engine
  val eventQueue: EventQueue = new EventQueue(engine, false)

  def runEngine(): Unit = {
    engine.addSystem(new VelocityUpdateSystem   (1))
    engine.addSystem(new EventQueueUpdateSystem (2, eventQueue))
    var lastUpdateTime = System.currentTimeMillis()
    while(true) {
      engine.update(System.currentTimeMillis() - lastUpdateTime)
      lastUpdateTime = System.currentTimeMillis()
      Thread.sleep(16)
    }
  }

  def handleObject(obj: Object): Unit = {
    obj match {
      case event: Event => eventQueue.addEvent(event)
      case _ =>
    }
  }

  def sendDataToNewClient(conn: WebSocket): Unit = {
    engine.getEntities.foreach(e =>
      conn.send(new String(Base64Coder.encode(KryoManager.encode(new EntityCreateEvent(0, e))))))

//    eventQueue.getAllEvents.foreach(e =>
//      conn.send(new String(Base64Coder.encode(KryoManager.encode(e)))))

    eventQueue.getAllEvents
      .map(KryoManager.encode)
      .map(Base64Coder.encode)
      .map(new String(_))
      .foreach(conn.send)
  }

  override def onError(conn: WebSocket, ex: Exception): Unit = {
    println("error with " + (if (conn!=null) conn.getRemoteSocketAddress else "null conn") + ":" + ex)
  }

  override def onMessage(conn: WebSocket, message: String): Unit = {
    println("received message from " + conn.getRemoteSocketAddress)
    connections.filter(_ != conn).foreach(_.send(message))

    handleObject(KryoManager.decode(Base64Coder.decode(message)))
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    println("closed " + conn.getRemoteSocketAddress + " with exit code " + code + " because: " + reason)
  }

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    println("opened " + conn.getRemoteSocketAddress)
    sendDataToNewClient(conn)
  }
}
