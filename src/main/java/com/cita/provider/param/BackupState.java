package com.cita.provider.param;

import com.google.gson.JsonObject;

public class BackupState {
    // 块高
    long blockHeight;

    public BackupState() {
    }

    public BackupState(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("blockHeight", this.blockHeight);
        return jsonObject;
    }
}
