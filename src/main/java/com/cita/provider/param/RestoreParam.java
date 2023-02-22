package com.cita.provider.param;

public class RestoreParam {

    // 恢复名
    String name;

    // 恢复CR所在的namespace，一般与节点同一namespace
    String namespace;

    // 恢复对象，链节点名
    String node;

    // 链节点的部署方式，python/cloud-config
    String deployMethod;

    // 链名
    String chain;

    // 恢复使用的备份名
    String backup;

    // 后端存储信息
    Backend backend;

    public RestoreParam() {
    }

    public RestoreParam(String name, String namespace, String node, String deployMethod, String chain, String backup, Backend backend) {
        this.name = name;
        this.namespace = namespace;
        this.node = node;
        this.deployMethod = deployMethod;
        this.chain = chain;
        this.backup = backup;
        this.backend = backend;
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

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public Backend getBackend() {
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }
}
