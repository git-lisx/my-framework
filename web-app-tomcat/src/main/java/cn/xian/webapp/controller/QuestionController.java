package cn.xian.webapp.controller;

import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @author lishixian
 * @date 2020/8/7 下午4:49
 */
@MyController
@MyRequestMapping("/question")
public class QuestionController {

    @MyRequestMapping("/get")
    public String getQues(String index) {

        List<String> questions = Arrays.asList(
                "当前我的项目是什么时候初始化bean？项目启动阶段还是第一次调用接口的时候？",
                "单例：获取对象的方法 instance() 是否需要使用static修饰？为啥？",
                "SpringAOP如何实现的AOP？代理类是在哪个阶段生成的？编译阶段还是程序运行阶段?",
                "SpringAOP 什么时候使用jdk代理，什么时候使用cglib代理？",
                "在操作多表时需要进行事务管理，使用@transactionnal属于声明式事务还是编程式事务？"
        );
        int indexValue = Integer.parseInt(index);
        if (indexValue < 1 || indexValue > questions.size()) {
            return "请输入数字0到" + questions.size();
        }
        return "请回答：" + questions.get(indexValue - 1);
    }


}
