package com.cita.provider.param;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BackendS3 {
    // s3 endpoint
    String endpoint;

    // s3 bucket
    String bucket;

    // accessKeyID
    AccessKeyIDSecret accessKeyIDSecretRef;

    // secretAccessKey
    SecretAccessKeySecret secretAccessKeySecretRef;

    public BackendS3() {
    }

    public BackendS3(String endpoint, String bucket, AccessKeyIDSecret accessKeyIDSecretRef, SecretAccessKeySecret secretAccessKeySecretRef) {
        this.endpoint = endpoint;
        this.bucket = bucket;
        this.accessKeyIDSecretRef = accessKeyIDSecretRef;
        this.secretAccessKeySecretRef = secretAccessKeySecretRef;
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

    public AccessKeyIDSecret getAccessKeyIDSecretRef() {
        return accessKeyIDSecretRef;
    }

    public void setAccessKeyIDSecretRef(AccessKeyIDSecret accessKeyIDSecretRef) {
        this.accessKeyIDSecretRef = accessKeyIDSecretRef;
    }

    public SecretAccessKeySecret getSecretAccessKeySecretRef() {
        return secretAccessKeySecretRef;
    }

    public void setSecretAccessKeySecretRef(SecretAccessKeySecret secretAccessKeySecretRef) {
        this.secretAccessKeySecretRef = secretAccessKeySecretRef;
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), JsonObject.class);
    }
}
