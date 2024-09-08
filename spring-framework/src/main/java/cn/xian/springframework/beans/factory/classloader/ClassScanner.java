package cn.xian.springframework.beans.factory.classloader;


import cn.xian.log.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 类扫描器，扫描项目中所有的class
 *
 * @author lishixian
 * @date 2019/10/16 上午10:34
 */
public class ClassScanner {

    private static final List<Class<?>> CLASSES = new ArrayList<>();

    public static List<Class<?>> getClasses(){
        return CLASSES;
    }

    /**
     * 扫描当前项目的所有class
     */
    public static void scan() {
        URL resource = ClassScanner.class.getResource("/");
        File file;
        Log.debug("类扫描的路径为:" + resource);
        try {
            assert resource != null;
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            Log.error(e.getMessage(), e);
            return;
        }
        Log.debug("类扫描的路径为:" + file);
        //使用队列来实现广度遍历,找出.class文件
        Queue<File> queue = new LinkedList<>();
        scanDirectoryAndInvokeClass(file, queue);
        while (!queue.isEmpty()) {
            File pollFile = queue.poll();
            scanDirectoryAndInvokeClass(pollFile, queue);
        }
        Log.debug(resource + "的class扫描完毕!");

    }

    /**
     * 扫描file下的一级文件夹加入到队列中，class文件解析成class添加到classes
     *
     * @param file           需扫描的file
     * @param directoryQueue 文件夹队列
     */
    private static void scanDirectoryAndInvokeClass(File file, Queue<File> directoryQueue) {
        File[] files = file.listFiles();
        assert files != null;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                //添加
                directoryQueue.offer(file1);
            } else if (file1.getName().endsWith(".class")) {
                try {
                    String absolutePath = file1.getAbsolutePath().replace(String.valueOf(File.separatorChar), ".");
                    String absoluteClassName = absolutePath.substring(absolutePath.indexOf("classes.") + 8, absolutePath.lastIndexOf(".class"));
                    CLASSES.add(Class.forName(absoluteClassName));
                } catch (ClassNotFoundException e) {
                    Log.warn(e.getMessage(), e);
                }
            }
        }
    }
}
