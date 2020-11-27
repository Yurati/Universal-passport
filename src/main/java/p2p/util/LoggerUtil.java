package p2p.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {

    public static final String LOGGER_NAME = "p2p.logging";

    static {
        Logger.getLogger(LOGGER_NAME).setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        Logger.getLogger(LOGGER_NAME).addHandler(handler);
    }

    public static void setHandlersLevel(Level level) {
        Handler[] handlers =
                Logger.getLogger(LOGGER_NAME).getHandlers();
        for (Handler h : handlers)
            h.setLevel(level);

        Logger.getLogger(LOGGER_NAME).setLevel(level);
    }

    public static Logger getLogger() {
        return Logger.getLogger(LOGGER_NAME);
    }
}

