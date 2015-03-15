package com.tsoft.civilization.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class DefaultLogger {
    private Logger logger;
    private FileHandler fileHandler;

    private static Map<Thread, DefaultLogger> loggers = new HashMap<Thread, DefaultLogger>();

    private DefaultLogger() { }

    public static void createLogger(String outputFileName) {
        DefaultLogger.createLogger(outputFileName, System.currentTimeMillis());
    }

    public static void createLogger(String outputFileName, long tick) {
        String name = "[" + tick + " " + Thread.currentThread().getName() + "]";
        if (getDefaultLogger() != null) {
            System.err.println("Logger " + name + ", " + outputFileName + " for " + Thread.currentThread().toString() + " already exists");
        } else {
            DefaultLogger defaultLogger = new DefaultLogger(name, outputFileName);
            loggers.put(Thread.currentThread(), defaultLogger);
        }
    }

    private DefaultLogger(String name, String outputFileName) {
        createFileHandler(outputFileName);

        logger = Logger.getLogger(name);
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
    }

    private void createFileHandler(String outputFileName) {
        String loggerDir = System.getProperty("user.home") + "/civilization/";
        new File(loggerDir).mkdirs();

        String logFileName = loggerDir + outputFileName + "-%u.log";
        try {
            fileHandler = new FileHandler(logFileName, true);
        } catch (IOException ex) {
            System.err.println("Can't create an output file for a logger: " + logFileName);
            throw new RuntimeException(ex);
        }

        fileHandler.setFormatter(new DefaultFormatter());
    }

    public static void fine(String msg) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.fine(msg);
        }
    }

    public static void info(String msg) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.info(msg);
        }
    }

    public static void warning(String msg) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.warning(msg);
        }
    }

    public static void severe(String msg) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.severe(msg);
        }
    }

    public static void severe(Throwable ex) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void severe(String msg, Throwable ex) {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.logger.log(Level.SEVERE, msg, ex);
            ex.printStackTrace();
        }
    }

    public static void close() {
        DefaultLogger defaultLogger = getDefaultLogger();
        if (defaultLogger != null) {
            defaultLogger.fileHandler.close();
            loggers.remove(Thread.currentThread());
        }
    }

    public static DefaultLogger getDefaultLogger() {
        return loggers.get(Thread.currentThread());
    }

    class DefaultFormatter extends SimpleFormatter {
        private final Date date = new Date();

        @Override
        public synchronized String format(LogRecord record) {
            date.setTime(record.getMillis());

            String message = formatMessage(record);
            String throwable = "";
            if (record.getThrown() != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                pw.println();
                record.getThrown().printStackTrace(pw);
                pw.close();
                throwable = sw.toString();
            }

            String className = Thread.currentThread().getStackTrace()[9].getClassName();
            String methodName = Thread.currentThread().getStackTrace()[9].getMethodName();
            int lineNumber = Thread.currentThread().getStackTrace()[9].getLineNumber();

            return String.format("%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s %3$s %4$s.java(%5$s:%6$d): %7$s%8$s%n",
                date, record.getLevel().getName(), record.getLoggerName(), className, methodName, lineNumber, message, throwable);
        }
    }
}
