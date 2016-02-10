package me.shreyasr.starfighter

import java.util.ArrayList

import com.badlogic.ashley.core.{Engine, Entity}
import me.shreyasr.starfighter.event.{Event, EventQueue}
import me.shreyasr.starfighter.network.GameState
import me.shreyasr.starfighter.systems.{EventQueueUpdateSystem, VelocityUpdateSystem}
import me.shreyasr.starfighter.util.JsonSerializer
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

import scala.collection.JavaConversions._
import scala.collection.mutable

class GameServer extends WebSocketServer {

  val engine = new Engine
  val eventQueue: EventQueue = new EventQueue(engine, false)
  val pastStates = mutable.TreeSet[String]()
  val newEvents = new ArrayList[Event]()

  def runEngine(): Unit = {
    eventQueue.setTime(System.currentTimeMillis())
    engine.addSystem(new EventQueueUpdateSystem   (1, eventQueue))
    engine.addSystem(new EventQueueNetworkUpdater (2, eventQueue, newEvents))
    engine.addSystem(new VelocityUpdateSystem     (3))
    engine.addSystem(new GameStateSendSystem      (4, 3000f, this))

    var lastUpdateTime = System.currentTimeMillis()
    while(true) {
      engine.update((System.currentTimeMillis() - lastUpdateTime)/1000f)
      lastUpdateTime = System.currentTimeMillis()
      Thread.sleep(16)
    }
  }

  def handleObject(obj: Object): Unit = {
    obj match {
      case event: Event => newEvents.synchronized { newEvents += event }
      case _ =>
    }
  }

  def addGameState(): Unit = {
    val gameState = JsonSerializer.encode(new GameState(
      System.currentTimeMillis(),
      engine.getEntities.toArray(classOf[Entity]),
      eventQueue.getAllEvents))
    pastStates.add(gameState)
  }

  def sendGameState(conn: WebSocket): Unit = {
    if (pastStates.nonEmpty) conn.send(pastStates.last)
  }

  override def onError(conn: WebSocket, ex: Exception): Unit = {
    println("error with " + (if (conn!=null) conn.getRemoteSocketAddress else "null conn") + ": " + ex)
  }

  override def onMessage(conn: WebSocket, message: String): Unit = {
    println("received message from " + conn.getRemoteSocketAddress)
    connections.filter(_ != conn).foreach(_.send(message))

    handleObject(JsonSerializer.decode(message))
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    println("closed " + conn.getRemoteSocketAddress + " with exit code " + code + " because: " + reason)
    sys.exit(0)
  }

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    println("opened " + conn.getRemoteSocketAddress)
    sendGameState(conn)
    addGameState()
  }
}
