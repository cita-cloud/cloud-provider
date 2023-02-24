package com.cita.cloud.provider.param;

public class SecretParam {
    String name;
    String namespace;
    String username;
    String password;

    public SecretParam() {
    }

    public SecretParam(String name, String namespace, String username, String password) {
        this.name = name;
        this.namespace = namespace;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private String username;
        private String password;

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public SecretParam build() {
            SecretParam secretParam = new SecretParam();
            secretParam.setName(name);
            secretParam.setNamespace(namespace);
            secretParam.setUsername(username);
            secretParam.setPassword(password);
            return secretParam;
        }
    }
}
