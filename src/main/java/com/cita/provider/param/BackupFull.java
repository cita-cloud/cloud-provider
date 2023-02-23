package com.cita.provider.param;


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
