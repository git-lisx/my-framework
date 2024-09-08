package cn.xian.springframework.context;

import cn.xian.log.Log;
import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;

/**
 * @author lishixian
 * @date 2020/8/8 下午7:58
 */
public class ApplicationContext {

    public static void init() {
        //扫描所有class
        Log.info("正在扫描所有class");
        ClassScanner.scan();
        Log.info("class扫描完毕！");

        //初始化bean工厂，装载bean（IOC控制反转）
        BeanFactory.getInstance().ioc();
        Log.info("初始化bean工厂，bean装载完毕！");
        //依赖注入（DI）
        BeanFactory.getInstance().injectDependency();
        Log.info("依赖注入完毕！");

        //建立uri与方法的映射关系
        UriFactory.getInstance().initUriMapping();
        Log.info("uri与方法的映射关系建立完毕！");
    }
}
