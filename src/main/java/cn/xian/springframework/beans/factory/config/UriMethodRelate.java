package cn.xian.springframework.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * uri与方法的映射
 *
 * @author lishixian
 * @date 2019/10/17 上午11:48
 */
@AllArgsConstructor
@Data
public class UriMethodRelate {

    private String uri;
    private String className;
    private String methodName;

}
