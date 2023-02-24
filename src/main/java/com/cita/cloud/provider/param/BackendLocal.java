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
import com.google.gson.JsonObject;

public class BackendLocal {
    // 挂载点
    String mountPath;

    // 备份PVC所使用的storage class
    String storageClass;

    public BackendLocal() {
    }

    public BackendLocal(String mountPath, String storageClass) {
        this.mountPath = mountPath;
        this.storageClass = storageClass;
    }

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), JsonObject.class);
    }
}
