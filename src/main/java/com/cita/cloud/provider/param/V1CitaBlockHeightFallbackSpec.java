package com.cita.cloud.provider.param;

public class V1CitaBlockHeightFallbackSpec {

    String chain;
    String node;
    String deployMethod;
    long blockHeight;

    public V1CitaBlockHeightFallbackSpec() {
    }

    public V1CitaBlockHeightFallbackSpec(String chain, String node, String deployMethod, long blockHeight) {
        this.chain = chain;
        this.node = node;
        this.deployMethod = deployMethod;
        this.blockHeight = blockHeight;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
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

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }
}
