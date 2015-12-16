package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;

import me.shreyasr.starfighter.event.CancelEvent;
import me.shreyasr.starfighter.event.Event;
import me.shreyasr.starfighter.event.MovementEvent;
import me.shreyasr.starfighter.event.TurnEvent;

public class KryoManager {

    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(Event.class);
        kryo.register(CancelEvent.class);
        kryo.register(MovementEvent.class);
        kryo.register(TurnEvent.class);
        kryo.addDefaultSerializer(Entity.class, new EntitySerializer());
    }

    public static Object decode(byte[] data) {
        return kryo.readClassAndObject(new Input(data));
    }

    public static byte[] encode(Object obj) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteOutputStream);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        return byteOutputStream.toByteArray();
    }
}
