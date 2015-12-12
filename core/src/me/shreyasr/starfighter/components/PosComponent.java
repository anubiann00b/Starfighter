package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

import java.text.DecimalFormat;

public class PosComponent implements Component {

    private static DecimalFormat format = new DecimalFormat("#0.00");

    public float x;
    public float y;

    @Override
    public String toString() {
        return "[" + format.format(x) + " " + format.format(y) + "]";
    }
}
