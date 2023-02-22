package com.cita.provider.param;

public class BackendLocal extends BackendType {
    // 挂载点
    String mountPath;

    // 备份PVC所使用的storage class
    String storageClass;

    public BackendLocal() {
        super(Type.LOCAL);
    }

    public BackendLocal(String mountPath, String storageClass) {
        super(Type.LOCAL);
        this.mountPath = mountPath;
        this.storageClass = storageClass;
    }

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }
}
