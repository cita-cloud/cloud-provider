package com.cita.provider.param;

public class AccessKeyIDSecret {
    String name;
    String key;

    public AccessKeyIDSecret() {
    }

    public AccessKeyIDSecret(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
