package com.cita.provider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1StatefulSet;

import static com.cita.provider.k8s.K8sInvokerClient.getAppApiObj;
import static com.cita.provider.k8s.K8sInvokerClient.getCoreApiObj;

public class Provider {
    public static void applyResources(String namespace, String resourcesJsonArrayStr) throws ApiException {
        AppsV1Api appV1Api = getAppApiObj();
        CoreV1Api coreV1Api = getCoreApiObj();
        Gson gson = new Gson();

        JsonArray resources = gson.fromJson(resourcesJsonArrayStr, JsonArray.class);
        for (JsonElement res : resources) {
            String kind = res.getAsJsonObject().get("kind").getAsString();
            switch (kind) {
                case "StatefulSet":
                    V1StatefulSet statefulSet = gson.fromJson(res, V1StatefulSet.class);
                    appV1Api.createNamespacedStatefulSet(namespace, statefulSet, null, null, null, null);
                    break;
                case "Service":
                    V1Service service = gson.fromJson(res, V1Service.class);
                    coreV1Api.createNamespacedService(namespace, service, null, null, null, null);
                    break;
                case "ConfigMap":
                    V1ConfigMap configMap = gson.fromJson(res, V1ConfigMap.class);
                    coreV1Api.createNamespacedConfigMap(namespace, configMap, null, null, null, null);
                    break;
                default:
                    break;
            }
        }
    }
}
