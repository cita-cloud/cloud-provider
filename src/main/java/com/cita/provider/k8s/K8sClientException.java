package com.cita.provider.k8s;

import java.util.Optional;

/**
 * @author FreeQ
 * @description <p>k8s调用相关的基础类，所有自定义k8s相关的异常类都需要继承本类</p>
 * @date 2023/1/16 9:45
 **/
public class K8sClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义详细异常信息
     */
    protected String detailMsg;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    public K8sClientException() {
        super("");
    }

    public K8sClientException(String detailMsg, Object... args) {
        super(Optional.ofNullable(detailMsg).orElse(""));
        this.args = args;
    }

    public K8sClientException(Throwable cause, String detailMsg, Object... args) {
        super(Optional.ofNullable(detailMsg).orElse(""), cause);
        this.args = args;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
