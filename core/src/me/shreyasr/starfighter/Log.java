package me.shreyasr.starfighter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {

    private static Logger logger = Logger.getLogger("Main Logger");
    static {
        if (Gdx.app.getType() != Application.ApplicationType.WebGL) {
            setConsoleHandler(logger);
        }
    }

    public static void e(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void i(String msg) {
        e(Gdx.app.getType().name());
        logger.log(Level.INFO, msg);
    }

    private static void setConsoleHandler(Logger logger) {
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

