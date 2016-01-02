package me.shreyasr.starfighter

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.utils.{Json, JsonValue}
import scala.collection.JavaConversions._

class ServerEntitySerializer extends Json.Serializer[Entity] {

  override def write(json: Json, entity: Entity, knownType: Class[_]): Unit = {
    val components: Array[Component] = entity.getComponents
      .filter(!_.isInstanceOf[ConnComponent])
      .toArray
    json.writeValue(components)
  }

  override def read(json: Json, jsonData: JsonValue, `type`: Class[_]): Entity = {
    val e: Entity = new Entity
    json.readValue(classOf[Array[Component]], jsonData).foreach(e.add); e
  }
}
