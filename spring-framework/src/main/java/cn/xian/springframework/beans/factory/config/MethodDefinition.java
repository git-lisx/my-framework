package cn.xian.springframework.beans.factory.config;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author lishixian
 * @date 2019/10/15 下午8:01
 */

@Data
public class MethodDefinition {
    private List<Class<?>> paramTypes;
    private Set<String> paramNames;
    private String methodName;
    private Method method;

    private MethodDefinition() {
    }

    public MethodDefinition(List<Class<?>> paramTypes, Set<String> paramNames, String methodName, Method method) {
        this.paramTypes = paramTypes;
        this.paramNames = paramNames;
        this.methodName = methodName;
        this.method = method;
    }
}
