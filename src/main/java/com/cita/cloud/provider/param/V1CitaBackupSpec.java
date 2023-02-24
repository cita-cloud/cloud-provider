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

public class V1CitaBackupSpec {
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

    public V1CitaBackupSpec() {
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
}
