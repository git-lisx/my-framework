package cn.xian.servlet.http;

public interface MyHttpSession {


    /**
     * 获取会话ID
     *
     * @return 会话ID
     */
    String getId();

    /**
     * 设置属性
     *
     * @param name  属性名
     * @param value 属性值
     */
    void setAttribute(String name, Object value);

    /**
     * 获取属性
     *
     * @param name 属性名
     * @return 属性值
     */
    Object getAttribute(String name);

    /**
     * 移除属性
     *
     * @param name 属性名
     */
    void removeAttribute(String name);

    /**
     * 销毁会话
     */
    void invalidate();


    /**
     * 获取最后访问时间
     *
     * @return 最后访问时间，单位毫秒 since 1/ 1/ 1970 GMT
     */
    long getLastAccessedTime();

    /**
     * 获取创建时间
     *
     * @return 创建时间，单位毫秒 since 1/ 1/ 1970 GMT
     */
    long getCreationTime();

    /**
     * 获取最大未使用时间
     *
     * @return 最大未使用时间，单位秒
     */
    int getMaxInactiveInterval();

    /**
     * 设置最大未使用时间
     *
     * @param interval 最大未使用时间，单位秒
     */
    void setMaxInactiveInterval(int interval);

}
