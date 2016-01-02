package me.shreyasr.starfighter

import com.badlogic.ashley.systems.IntervalSystem

import scala.collection.JavaConversions._

class GameStateSendSystem(priority: Int, interval: Float,
                          gameServer: GameServer)
  extends IntervalSystem(interval, priority) {

  override def updateInterval(): Unit = {
    gameServer.addGameState()
    if (gameServer.pastStates.nonEmpty) {
      gameServer.connections.foreach(_.send(gameServer.pastStates.last))
    }
  }
}
