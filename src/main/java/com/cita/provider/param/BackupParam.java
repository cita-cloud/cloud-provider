package com.cita.provider.param;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BackupParam {

    // 备份名
    String name;

    // 备份CR所在的namespace，一般与节点同一namespace
    String namespace;

    // 备份对象，链节点名
    String node;

    // 链节点的部署方式，python/cloud-config
    String deployMethod;

    // 链名
    String chain;

    // 备份数据类型, full/state
    JsonObject dataType;

    // 失败的任务最大保留个数
    int failedJobsHistoryLimit = 2;

    // 成功的任务最大保留个数
    int successfulJobsHistoryLimit = 2;

    // 后端存储信息
    Backend backend;

    public BackupParam() {
    }

    public BackupParam(String name, String namespace, String node, String deployMethod, String chain, BackupDataType dataType, int failedJobsHistoryLimit, int successfulJobsHistoryLimit, Backend backend) {
        this.name = name;
        this.namespace = namespace;
        this.node = node;
        this.deployMethod = deployMethod;
        this.chain = chain;
        this.setDataType(dataType);
        this.failedJobsHistoryLimit = failedJobsHistoryLimit;
        this.successfulJobsHistoryLimit = successfulJobsHistoryLimit;
        this.setBackend(backend);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getDeployMethod() {
        return deployMethod;
    }

    public void setDeployMethod(String deployMethod) {
        this.deployMethod = deployMethod;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public JsonObject getDataType() {
        return dataType;
    }

    public void setDataType(BackupDataType dataType) {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        switch (dataType.type) {
            case FULL:
                BackupFull backupFull = (BackupFull) dataType;
                object.addProperty("full", gson.toJson(backupFull));
                break;
            case STATE:
                BackupState backupState = (BackupState) dataType;
                object.addProperty("state", gson.toJson(backupState));
        }
        this.dataType = object;
    }

    public int getFailedJobsHistoryLimit() {
        return failedJobsHistoryLimit;
    }

    public void setFailedJobsHistoryLimit(int failedJobsHistoryLimit) {
        this.failedJobsHistoryLimit = failedJobsHistoryLimit;
    }

    public int getSuccessfulJobsHistoryLimit() {
        return successfulJobsHistoryLimit;
    }

    public void setSuccessfulJobsHistoryLimit(int successfulJobsHistoryLimit) {
        this.successfulJobsHistoryLimit = successfulJobsHistoryLimit;
    }

    public Backend getBackend() {
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
