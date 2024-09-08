package cn.xian.springframework.beans.factory.config;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author lishixian
 * @date 2019/10/15 下午8:01
 */

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

    public List<Class<?>> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<Class<?>> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Set<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(Set<String> paramNames) {
        this.paramNames = paramNames;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
