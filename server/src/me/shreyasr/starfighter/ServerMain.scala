package me.shreyasr.starfighter

object ServerMain {

  def main(args: Array[String]) {
    val server: GameServer = new GameServer
    new Thread(server).start()
    server.runEngine()
  }
}
