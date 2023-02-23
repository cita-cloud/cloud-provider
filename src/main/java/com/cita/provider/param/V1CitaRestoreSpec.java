package com.cita.provider.param;

public class V1CitaRestoreSpec {
    // 备份对象，链节点名
    String node;

    // 链节点的部署方式，python/cloud-config
    String deployMethod;

    // 链名
    String chain;

    // 恢复使用的备份名
    String backup;

    // 后端存储信息
    Backend backend;

    public V1CitaRestoreSpec() {
    }

}
