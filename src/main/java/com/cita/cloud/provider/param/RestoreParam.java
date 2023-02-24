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

    public static final class Builder {
        private String name;
        private String namespace;
        private String node;
        private String deployMethod;
        private String chain;
        private String backup;
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

        public Builder backup(String backup) {
            this.backup = backup;
            return this;
        }

        public Builder backend(Backend backend) {
            this.backend = backend;
            return this;
        }

        public RestoreParam build() {
            RestoreParam restoreParam = new RestoreParam();
            restoreParam.setName(name);
            restoreParam.setNamespace(namespace);
            restoreParam.setNode(node);
            restoreParam.setDeployMethod(deployMethod);
            restoreParam.setChain(chain);
            restoreParam.setBackup(backup);
            restoreParam.setBackend(backend);
            return restoreParam;
        }
    }
}
