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

import java.util.Map;

public class SecretParam {
    String name;
    String namespace;
    Map<String, String> stringData;
    Map<String, byte[]> data;

    public SecretParam() {
    }

    public SecretParam(String name, String namespace, Map<String, String> stringData, Map<String, byte[]> data) {
        this.name = name;
        this.namespace = namespace;
        this.stringData = stringData;
        this.data = data;
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

    public Map<String, String> getStringData() {
        return stringData;
    }

    public void setStringData(Map<String, String> stringData) {
        this.stringData = stringData;
    }

    public Map<String, byte[]> getData() {
        return data;
    }

    public void setData(Map<String, byte[]> data) {
        this.data = data;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private Map<String, String> stringData;
        private Map<String, byte[]> data;

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

        public Builder stringData(Map<String, String> stringData) {
            this.stringData = stringData;
            return this;
        }

        public Builder data(Map<String, byte[]> data) {
            this.data = data;
            return this;
        }

        public SecretParam build() {
            SecretParam secretParam = new SecretParam();
            secretParam.setName(name);
            secretParam.setNamespace(namespace);
            secretParam.setStringData(stringData);
            secretParam.setData(data);
            return secretParam;
        }
    }
}
