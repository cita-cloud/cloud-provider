// Copyright Rivtower Technologies LLC.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cita.cloud.provider.param;

import com.google.gson.Gson;

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
    BackupDataType dataType;

    // 失败的任务最大保留个数
    int failedJobsHistoryLimit;

    // 成功的任务最大保留个数
    int successfulJobsHistoryLimit;

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
        this.dataType = dataType;
        this.failedJobsHistoryLimit = failedJobsHistoryLimit;
        this.successfulJobsHistoryLimit = successfulJobsHistoryLimit;
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

    public BackupDataType getDataType() {
        return dataType;
    }

    public void setDataType(BackupDataType dataType) {
        this.dataType = dataType;
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

    public static final class Builder {
        private String name;
        private String namespace;
        private String node;
        private String deployMethod;
        private String chain;
        private BackupDataType dataType;
        private int failedJobsHistoryLimit;
        private int successfulJobsHistoryLimit;
        private Backend backend;

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

        public Builder node(String node) {
            this.node = node;
            return this;
        }

        public Builder deployMethod(String deployMethod) {
            this.deployMethod = deployMethod;
            return this;
        }

        public Builder chain(String chain) {
            this.chain = chain;
            return this;
        }

        public Builder dataType(BackupDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder failedJobsHistoryLimit(int failedJobsHistoryLimit) {
            this.failedJobsHistoryLimit = failedJobsHistoryLimit;
            return this;
        }

        public Builder successfulJobsHistoryLimit(int successfulJobsHistoryLimit) {
            this.successfulJobsHistoryLimit = successfulJobsHistoryLimit;
            return this;
        }

        public Builder backend(Backend backend) {
            this.backend = backend;
            return this;
        }

        public BackupParam build() {
            BackupParam backupParam = new BackupParam();
            backupParam.setName(name);
            backupParam.setNamespace(namespace);
            backupParam.setNode(node);
            backupParam.setDeployMethod(deployMethod);
            backupParam.setChain(chain);
            backupParam.setDataType(dataType);
            backupParam.setFailedJobsHistoryLimit(failedJobsHistoryLimit);
            backupParam.setSuccessfulJobsHistoryLimit(successfulJobsHistoryLimit);
            backupParam.setBackend(backend);
            return backupParam;
        }
    }
}
