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

package com.cita.cloud.provider;

import com.cita.cloud.provider.param.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.*;

import static com.cita.cloud.provider.k8s.K8sInvokerClient.*;

public class Provider {
    public static void applyResources(String namespace, String resourcesJsonArrayStr) throws ApiException {
        AppsV1Api appV1Api = getAppApiObj();
        CoreV1Api coreV1Api = getCoreApiObj();
        CustomObjectsApi customApi = getCustomApiObj();
        Gson gson = new Gson();

        JsonArray resources = gson.fromJson(resourcesJsonArrayStr, JsonArray.class);
        for (JsonElement res : resources) {
            System.out.println("resJson: " + res);
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
                case "Secret":
                    V1Secret secret = gson.fromJson(res, V1Secret.class);
                    coreV1Api.createNamespacedSecret(namespace, secret, null, null, null, null);
                    break;
                case "Backup":
                    V1CitaBackup backup = gson.fromJson(res, V1CitaBackup.class);
                    System.out.println("V1CitaBackup: " + gson.toJson(backup));
                    customApi.createNamespacedCustomObject("rivtower.com", "v1cita", namespace, "backups",
                            gson.fromJson(res, V1CitaBackup.class), null, null, null);
                    break;
                case "Restore":
                    customApi.createNamespacedCustomObject("rivtower.com", "v1cita", namespace, "restores",
                            res, null, null, null);
                    break;
                default:
                    throw new ApiException(400, "not support for the kind of resource: " + kind);
            }
        }
    }

    public static void backup(BackupParam param) throws ApiException {
        JsonObject backupRes = new JsonObject();
        backupRes.addProperty("apiVersion", "rivtower.com/v1cita");
        backupRes.addProperty("kind", "Backup");

        // metadata
        JsonObject metadata = new JsonObject();
        metadata.addProperty("name", param.getName());
        metadata.addProperty("namespace", param.getNamespace());
        backupRes.add("metadata", metadata);

        // spec
        JsonObject spec = new JsonObject();
        spec.addProperty("node", param.getNode());
        spec.addProperty("deployMethod", param.getDeployMethod());
        spec.addProperty("chain", param.getChain());
        spec.addProperty("failedJobsHistoryLimit", 3);
        spec.addProperty("successfulJobsHistoryLimit", 3);

        // dataType
        BackupDataType backupDataType = param.getDataType();
        JsonObject dataType = new JsonObject();
        if (backupDataType.getFull() != null) {
            dataType.add("full", backupDataType.getFull().toJson());
        }
        if (backupDataType.getState() != null) {
            dataType.add("state", backupDataType.getState().toJson());
        }
        spec.add("dataType", dataType);

        // backend
        Backend backend = param.getBackend();
        JsonObject backendRes = new JsonObject();
        // backend secret
        JsonObject backendSecret = new JsonObject();
        backendSecret.addProperty("name", backend.getRepoPasswordSecretRef().getName());
        backendSecret.addProperty("key", backend.getRepoPasswordSecretRef().getKey());
        backendRes.add("repoPasswordSecretRef", backendSecret);
        // backend type
        if (backend.getLocal() != null) {
            backendRes.add("local", backend.getLocal().toJson());
        }
        if (backend.getS3() != null) {
            backendRes.add("s3", backend.getS3().toJson());
        }
        spec.add("backend", backendRes);

        backupRes.add("spec", spec);

        JsonArray resources = new JsonArray(1);
        resources.add(backupRes);
        applyResources(param.getNamespace(), resources.toString());
    }

    public static void restore(RestoreParam param) throws ApiException {
        V1CitaRestoreSpec restoreSpec = new V1CitaRestoreSpec();
        restoreSpec.setChain(param.getChain());
        restoreSpec.setNode(param.getNode());
        restoreSpec.setBackup(param.getBackup());
        restoreSpec.setDeployMethod(param.getDeployMethod());
        restoreSpec.setBackend(param.getBackend());
        restoreSpec.setRestoreMethod(param.getNode());

        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setNamespace(param.getNamespace());
        meta.setName(param.getName());
        V1CitaRestore restore = new V1CitaRestore(meta, restoreSpec);

        Gson gson = new Gson();
        JsonArray resources = new JsonArray(1);
        resources.add(gson.fromJson(gson.toJson(restore), JsonObject.class));
        applyResources(param.getNamespace(), resources.toString());
    }
}
