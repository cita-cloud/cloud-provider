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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class BackupFull {
    // 需要备份的目录列表
    List<String> includePaths;

    public BackupFull() {
    }

    public BackupFull(List<String> includePaths) {
        this.includePaths = includePaths;
    }

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = includePaths;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonArray includePaths = new JsonArray();
        for (String path : this.includePaths) {
            includePaths.add(path);
        }
        jsonObject.add("includePaths", includePaths);
        return jsonObject;
    }
}
