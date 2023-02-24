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

import com.google.gson.JsonObject;

public class V1CitaRestoreSpec {
    // 备份对象，链节点名
    String node;

    // 链节点的部署方式，python/cloud-config
    String deployMethod;

    // 链名
    String chain;

    // 恢复使用的备份名
    String backup;

    JsonObject restoreMethod;

    // 后端存储信息
    Backend backend;

    public V1CitaRestoreSpec() {
    }

    public V1CitaRestoreSpec(String node, String deployMethod, String chain, String backup, Backend backend) {
        this.node = node;
        this.deployMethod = deployMethod;
        this.chain = chain;
        this.backup = backup;
        this.backend = backend;
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

    public JsonObject getRestoreMethod() {
        JsonObject folder = new JsonObject();
        folder.addProperty("claimName", "datadir-" + getNode() + "-0");
        JsonObject restoreMethod = new JsonObject();
        restoreMethod.add("folder", folder);
        return restoreMethod;
    }

    public void setRestoreMethod(String node) {
        JsonObject folder = new JsonObject();
        folder.addProperty("claimName", "datadir-" + node + "-0");
        JsonObject restoreMethod = new JsonObject();
        restoreMethod.add("folder", folder);
        this.restoreMethod = restoreMethod;
    }
}
