package cn.xian.springframework.beans.factory.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用javassist字节码增强技术，通过class获取方法的参数名
 *
 * @author lishixian
 * @date 2019/10/17 下午7:26
 */
public class ClassUtil {
    /**
     * 获取参数名称
     *
     * @param method 方法实例
     * @param clazz  字节码
     * @return 当前方法的所有参数名
     */
    public static List<String> getParamNames(Method method, Class<?> clazz) {
        List<String> paramNames = new ArrayList<>();

        if (method.getParameters().length == 0) {
            return paramNames;
        }

        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());

            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int len = ctMethod.getParameterTypes().length;
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                for (int i = 0; i < len; i++) {
                    paramNames.add(attr.variableName(i + pos));
                }
            }
            return paramNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
