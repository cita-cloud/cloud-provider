package com.cita.provider.param;

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ObjectMeta;

public class V1CitaBackup implements KubernetesObject {
    String apiVersion;
    String kind;
    V1ObjectMeta metadata;
    V1CitaBackupSpec spec;

    @Override
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }

    @Override
    public String getApiVersion() {
        return "v1cita";
    }

    @Override
    public String getKind() {
        return "Backup";
    }
}
