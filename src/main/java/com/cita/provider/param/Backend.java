package com.cita.provider.param;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Backend {
    // 备份仓库对应的secret
    String repoPasswordSecretName;
    String repoPasswordSecretKey;

    // 后端存储的类型：local/s3
    JsonObject backendType;

    public Backend() {
    }

    public Backend(String repoPasswordSecretName, String repoPasswordSecretKey, BackendType backendType) {
        this.repoPasswordSecretName = repoPasswordSecretName;
        this.repoPasswordSecretKey = repoPasswordSecretKey;
        this.setBackendType(backendType);
    }

    public String getRepoPasswordSecretName() {
        return repoPasswordSecretName;
    }

    public void setRepoPasswordSecretName(String repoPasswordSecretName) {
        this.repoPasswordSecretName = repoPasswordSecretName;
    }

    public String getRepoPasswordSecretKey() {
        return repoPasswordSecretKey;
    }

    public void setRepoPasswordSecretKey(String repoPasswordSecretKey) {
        this.repoPasswordSecretKey = repoPasswordSecretKey;
    }

    public JsonObject getBackendType() {
        return backendType;
    }

    public void setBackendType(BackendType backendType) {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        switch (backendType.type) {
            case LOCAL:
                BackendLocal backendLocal = (BackendLocal) backendType;
                object.addProperty("local", gson.toJson(backendLocal));
                break;
            case S3:
                BackendS3 backendS3 = (BackendS3) backendType;
                object.addProperty("s3", gson.toJson(backendS3));
        }
        this.backendType = object;
    }
}
