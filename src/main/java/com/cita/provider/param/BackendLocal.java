package com.cita.provider.param;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BackendLocal {
    // 挂载点
    String mountPath;

    // 备份PVC所使用的storage class
    String storageClass;

    public BackendLocal() {
    }

    public BackendLocal(String mountPath, String storageClass) {
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

    public JsonObject toJson() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), JsonObject.class);
    }
}
