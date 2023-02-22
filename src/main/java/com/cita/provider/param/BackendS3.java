package com.cita.provider.param;

public class BackendS3 extends BackendType {
    // s3 endpoint
    String endpoint;

    // s3 bucket
    String bucket;

    // accessKeyID
    String accessKeyIDSecretName;
    String accessKeyIDSecretKey;

    // secretAccessKey
    String secretAccessKeySecretName;
    String secretAccessKeySecretKey;

    public BackendS3() {
        super(Type.S3);
    }

    public BackendS3(String endpoint, String bucket, String accessKeyIDSecretName, String accessKeyIDSecretKey, String secretAccessKeySecretName, String secretAccessKeySecretKey) {
        super(Type.S3);
        this.endpoint = endpoint;
        this.bucket = bucket;
        this.accessKeyIDSecretName = accessKeyIDSecretName;
        this.accessKeyIDSecretKey = accessKeyIDSecretKey;
        this.secretAccessKeySecretName = secretAccessKeySecretName;
        this.secretAccessKeySecretKey = secretAccessKeySecretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKeyIDSecretName() {
        return accessKeyIDSecretName;
    }

    public void setAccessKeyIDSecretName(String accessKeyIDSecretName) {
        this.accessKeyIDSecretName = accessKeyIDSecretName;
    }

    public String getAccessKeyIDSecretKey() {
        return accessKeyIDSecretKey;
    }

    public void setAccessKeyIDSecretKey(String accessKeyIDSecretKey) {
        this.accessKeyIDSecretKey = accessKeyIDSecretKey;
    }

    public String getSecretAccessKeySecretName() {
        return secretAccessKeySecretName;
    }

    public void setSecretAccessKeySecretName(String secretAccessKeySecretName) {
        this.secretAccessKeySecretName = secretAccessKeySecretName;
    }

    public String getSecretAccessKeySecretKey() {
        return secretAccessKeySecretKey;
    }

    public void setSecretAccessKeySecretKey(String secretAccessKeySecretKey) {
        this.secretAccessKeySecretKey = secretAccessKeySecretKey;
    }
}
