package me.shreyasr.starfighter

import me.shreyasr.starfighter.util.JsonSerializer

object ServerMain {

  def main(args: Array[String]) {
    JsonSerializer.init()
    val server: GameServer = new GameServer
    new Thread(server).start()
    server.runEngine()
  }
}
