package keqing.gtmaid.api;

import org.apache.logging.log4j.Logger;

public class GMLog {
    public static Logger logger;

    public GMLog() {
    }

    public static void init(Logger modLogger) {
        logger = modLogger;
    }
}
