package me.shreyasr.starfighter.util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {

    private static Logger logger = Logger.getLogger("Main Logger");

    public static void e(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void i(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static void v(String msg) {
        logger.log(Level.FINE, msg);
    }

    public static void e(Throwable ex) {
        e(ex.toString());
    }

    public static void i(Throwable ex) {
        i(ex.toString());
    }

    public static void v(Throwable ex) {
        v(ex.toString());
    }

    public static void setConsoleHandler() {
        logger.setUseParentHandlers(false);
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                System.out.println(record.getLevel().getName() + ": "
                        + record.getMessage());
            }

            @Override
            public void flush() {
                System.out.flush();
            }

            @Override
            public void close() throws SecurityException {

            }
        });
    }
}

