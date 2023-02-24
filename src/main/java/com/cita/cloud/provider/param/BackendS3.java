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
