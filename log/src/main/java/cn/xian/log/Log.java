package cn.xian.log;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.xian.log.ConsoleColors.*;

public class Log {


    private static final String LEVEL_ERROR = RED + "ERROR" + RESET;
    private static final String LEVEL_WARN = YELLOW + "WARN " + RESET;
    private static final String LEVEL_DEBUG = PURPLE + "DEBUG" + RESET;
    private static final String LEVEL_INFO = GREEN_BOLD + "INFO " + RESET;

    public static void error(String content, Throwable throwable) {
        print(content, LEVEL_ERROR, true);
        printThrowable(throwable);
    }

    private static void printThrowable(Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void error(String content, Object object) {
        String log = getLog(content, object);

        print(log, LEVEL_ERROR, true);
    }

    public static void error(String content) {
        print(content, LEVEL_ERROR, true);
    }

    public static void warn(String content) {
        print(content, LEVEL_WARN, false);
    }

    public static void warn(String content, Throwable throwable) {
        print(content, LEVEL_WARN, false);
        printThrowable(throwable);
    }

    public static void warn(String content, Object object) {
        String log = getLog(content, object);
        print(log, LEVEL_WARN, false);
    }

    private static String getLog(String content, Object object) {
        return String.format(content.replaceAll("\\{}", "%s"), object);
    }

    public static void debug(String content) {
        print(content, LEVEL_DEBUG, false);
    }

    public static void debug(String content, Throwable throwable) {
        print(content, LEVEL_DEBUG, false);
        printThrowable(throwable);
    }

    public static void debug(String content, Object object) {
        String log = getLog(content, object);
        print(log, LEVEL_DEBUG, false);
    }

    public static void info(String content) {
        print(content, LEVEL_INFO, false);
    }

    public static void info(String content, Throwable throwable) {
        print(content, LEVEL_INFO, false);
        printThrowable(throwable);
    }

    private static void print(String content, String level, boolean red) {
        // 获取当前线程的调用栈信息
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // 第 0 个是 getStackTrace 自己，1 是 printCallerClass 方法，2 是调用 printCallerClass 的类和方法
        if (stackTraceElements.length >= 4) {
            String callerClassName = stackTraceElements[3].getClassName(); // 获取调用者的类名
            String callerMethodName = stackTraceElements[3].getMethodName(); // 获取调用者的方法名

            String name = Thread.currentThread().getName();
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            String log = String.format("%s %s --- [%s] %s#%s %s",
                    dateTime, level, name, callerClassName, callerMethodName, content);
            System.out.println(log);
        }
    }
}
