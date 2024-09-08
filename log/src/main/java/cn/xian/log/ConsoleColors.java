package cn.xian.log;

public class ConsoleColors {
    // ANSI 转义码常量
    public static final String RESET = "\033[0m";  // 重置颜色
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static final String BLACK_BOLD = "\033[1;30m";  // 粗体黑色
    public static final String RED_BOLD = "\033[1;31m";    // 粗体红色
    public static final String GREEN_BOLD = "\033[1;32m";  // 粗体绿色
    public static final String YELLOW_BOLD = "\033[1;33m"; // 粗体黄色
    public static final String BLUE_BOLD = "\033[1;34m";   // 粗体蓝色
    public static final String PURPLE_BOLD = "\033[1;35m"; // 粗体紫色
    public static final String CYAN_BOLD = "\033[1;36m";   // 粗体青色
    public static final String WHITE_BOLD = "\033[1;37m";  // 粗体白色
    public static final String GRAY = "\033[0;90m"; // 灰色（接近默认白色）


    public static void main(String[] args) {
        System.out.println(RED + "这是红色文本" + RESET);
        System.out.println(GREEN_BOLD + "这是粗体绿色文本" + RESET);
        System.out.println(YELLOW + "这是黄色文本" + RESET);
        System.out.println(BLUE + "这是蓝色文本" + RESET);
        System.out.println(PURPLE + "这是紫色文本" + RESET);
        System.out.println(CYAN + "这是青色文本" + RESET);
        System.out.println(WHITE + "这是白色文本" + RESET);
        System.out.println(GRAY + "这是灰色文本，接近控制台默认白色" + RESET);
        System.out.println("这是终端默认颜色文本"); // 没有设置颜色，使用终端默认颜色


    }
}
