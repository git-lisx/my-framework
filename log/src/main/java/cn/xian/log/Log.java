package cn.xian.log;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.xian.log.ConsoleColors.RED;
import static cn.xian.log.ConsoleColors.RESET;

public class Log {

    public static void error(String content, Throwable throwable) {
        print(content, "ERROR", true);
        printThrowable(throwable);
    }

    private static void printThrowable(Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void error(String content, Object object) {
        String log = getLog(content, object);
        print(log, "ERROR", true);
    }

    public static void error(String content) {
        print(content, "ERROR", true);
    }

    public static void warn(String content) {
        print(content, "WARN", false);
    }

    public static void warn(String content, Throwable throwable) {
        print(content, "WARN", false);
        printThrowable(throwable);
    }

    public static void warn(String content, Object object) {
        String log = getLog(content, object);
        print(log, "WARN", false);
    }

    private static String getLog(String content, Object object) {
        return String.format(content.replaceAll("\\{}","%s"), object);
    }

    public static void debug(String content) {
        print(content, "DEBUG", false);
    }

    public static void debug(String content, Throwable throwable) {
        print(content, "DEBUG", false);
        printThrowable(throwable);
    }

    public static void debug(String content, Object object) {
        String log = getLog(content, object);
        print(log, "DEBUG", false);
    }

    public static void info(String content) {
        print(content, "INFO", false);
    }

    public static void info(String content, Throwable throwable) {
        print(content, "INFO", false);
        printThrowable(throwable);
    }

    private static void print(String content, String level, boolean red) {
        // 获取当前线程的调用栈信息
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // 第 0 个是 getStackTrace 自己，1 是 printCallerClass 方法，2 是调用 printCallerClass 的类和方法
        if (stackTraceElements.length >= 3) {
            String callerClassName = stackTraceElements[2].getClassName(); // 获取调用者的类名
            String callerMethodName = stackTraceElements[2].getMethodName(); // 获取调用者的方法名

            String name = Thread.currentThread().getName();
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String log = String.format("[%s] %s %s class: %s, method: %s %s",
                    name, level, dateTime, callerClassName, callerMethodName, content);
            if (red) {
                System.out.println(RED + log + RESET);
            } else {
                System.out.println(log);
            }
        }
    }
}
