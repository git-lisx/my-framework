package cn.xian.springframework.beans.factory.config;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lishixian
 * @date 2019/10/15 下午8:01
 */
@Data
public class MethodDefinition {
    private List<Class> paramTypes;
    private List<String> paramNames;
    private String methodName;
    private Method method;

    public MethodDefinition() {
        paramTypes = new ArrayList<>();
        paramNames = new ArrayList<>();
    }
}
