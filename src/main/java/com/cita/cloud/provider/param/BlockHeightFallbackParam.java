package com.cita.cloud.provider.param;

public class BlockHeightFallbackParam {
    String name;
    String namespace;
    String chain;
    String node;
    String deployMethod;
    long blockHeight;

    public BlockHeightFallbackParam() {
    }

    public BlockHeightFallbackParam(String name, String namespace, String chain, String node, String deployMethod, long blockHeight) {
        this.name = name;
        this.namespace = namespace;
        this.chain = chain;
        this.node = node;
        this.deployMethod = deployMethod;
        this.blockHeight = blockHeight;
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

    public static final class Builder {
        private String name;
        private String namespace;
        private String chain;
        private String node;
        private String deployMethod;
        private long blockHeight;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder chain(String chain) {
            this.chain = chain;
            return this;
        }

        public Builder node(String node) {
            this.node = node;
            return this;
        }

        public Builder deployMethod(String deployMethod) {
            this.deployMethod = deployMethod;
            return this;
        }

        public Builder blockHeight(long blockHeight) {
            this.blockHeight = blockHeight;
            return this;
        }

        public BlockHeightFallbackParam build() {
            BlockHeightFallbackParam blockHeightFallbackParam = new BlockHeightFallbackParam();
            blockHeightFallbackParam.setName(name);
            blockHeightFallbackParam.setNamespace(namespace);
            blockHeightFallbackParam.setChain(chain);
            blockHeightFallbackParam.setNode(node);
            blockHeightFallbackParam.setDeployMethod(deployMethod);
            blockHeightFallbackParam.setBlockHeight(blockHeight);
            return blockHeightFallbackParam;
        }
    }
}
