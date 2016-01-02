package me.shreyasr.starfighter

import java.util.ArrayList

import com.badlogic.ashley.core.EntitySystem
import me.shreyasr.starfighter.event.{Event, EventQueue}

import scala.collection.JavaConversions._

class EventQueueNetworkUpdater(priority: Int, eventQueue: EventQueue, newEvents: ArrayList[Event])
  extends EntitySystem(priority) {

  override def update(deltaTime: Float): Unit = {
    newEvents.synchronized {
      newEvents.foreach(eventQueue.addEvent)
      newEvents.clear()
    }
  }
}
