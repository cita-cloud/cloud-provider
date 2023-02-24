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

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ObjectMeta;

public class V1CitaRestore implements KubernetesObject {
    String apiVersion;
    String kind;
    V1ObjectMeta metadata;
    V1CitaRestoreSpec spec;

    public V1CitaRestore() {
    }

    public V1CitaRestore(V1ObjectMeta metadata, V1CitaRestoreSpec spec) {
        this.apiVersion = "rivtower.com/v1cita";
        this.kind = "Restore";
        this.metadata = metadata;
        this.spec = spec;
    }

    @Override
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }

    public void setMetadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getApiVersion() {
        return "rivtower.com/v1cita";
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public String getKind() {
        return "Restore";
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public V1CitaRestoreSpec getSpec() {
        return spec;
    }

    public void setSpec(V1CitaRestoreSpec spec) {
        this.spec = spec;
    }
}
