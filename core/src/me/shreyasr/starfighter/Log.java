package me.shreyasr.starfighter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    private static Logger logger = Logger.getLogger("");

    public static void e(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void i(String msg) {
        logger.log(Level.INFO, msg);
    }
}

