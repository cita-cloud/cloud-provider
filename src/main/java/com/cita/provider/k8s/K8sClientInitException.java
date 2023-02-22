package com.cita.provider.k8s;

/**
 * k8s操作客户端初始化异常
 *
 * @author : FreeQ
 * @version : 1.0
 * @date : 2023/1/16 9:46
 **/
public class K8sClientInitException extends K8sClientException {

    private static final long serialVersionUID = 1L;

    public K8sClientInitException() {
        super();
    }

    public K8sClientInitException(String detailMsg) {
        super(detailMsg);
    }

    public K8sClientInitException(String detailMsg, Object... args) {
        super(detailMsg, args);
    }

    public K8sClientInitException(Throwable cause, String detailMsg, Object... args) {
        super(cause, detailMsg, args);
    }


}
