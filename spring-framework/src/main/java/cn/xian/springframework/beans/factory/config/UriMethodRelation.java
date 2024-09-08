package cn.xian.springframework.beans.factory.config;


/**
 * uri与方法的映射
 *
 * @author lishixian
 * @date 2019/10/17 上午11:48
 */
public class UriMethodRelation {

    private String uri;
    private String beanId;
    private String methodName;

    public UriMethodRelation(String uri, String beanId, String methodName) {
        this.uri = uri;
        this.beanId = beanId;
        this.methodName = methodName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
