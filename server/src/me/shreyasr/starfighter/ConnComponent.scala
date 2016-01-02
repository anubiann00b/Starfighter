package me.shreyasr.starfighter

import com.badlogic.ashley.core.Component
import org.java_websocket.WebSocket

class ConnComponent extends Component {

  var conn: WebSocket = null
}
