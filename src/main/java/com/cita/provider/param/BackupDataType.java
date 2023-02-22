package com.cita.provider.param;

public class BackupDataType {
    transient Type type;

    public BackupDataType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        FULL, STATE,
    }
}

