package com.cita.cloud.provider.param;

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ObjectMeta;

public class V1CitaBlockHeightFallback implements KubernetesObject {

    String apiVersion;
    String kind;
    V1ObjectMeta metadata;
    V1CitaBlockHeightFallbackSpec spec;

    public V1CitaBlockHeightFallback() {
    }

    public V1CitaBlockHeightFallback(V1ObjectMeta metadata, V1CitaBlockHeightFallbackSpec spec) {
        this.apiVersion = "rivtower.com/v1cita";
        this.kind = "BlockHeightFallback";
        this.metadata = metadata;
        this.spec = spec;
    }

    @Override
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }

    public void setMetadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
    }

    @Override
    public String getApiVersion() {
        return "rivtower.com/v1cita";
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public String getKind() {
        return "BlockHeightFallback";
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public V1CitaBlockHeightFallbackSpec getSpec() {
        return spec;
    }

    public void setSpec(V1CitaBlockHeightFallbackSpec spec) {
        this.spec = spec;
    }
}
