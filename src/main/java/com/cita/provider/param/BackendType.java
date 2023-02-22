package com.cita.provider.param;

public class BackendType {
    transient Type type;

    public BackendType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        LOCAL, S3,
    }
}
