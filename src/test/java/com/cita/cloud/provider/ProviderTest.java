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
import io.kubernetes.client.openapi.ApiException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.cita.cloud.provider.Provider.backup;
import static com.cita.cloud.provider.Provider.restore;
import static com.cita.cloud.provider.k8s.K8sInvokerClient.initApiClient;

class ProviderTest {
    @Test
    void testBackup() {
        // backupDataType
        BackupFull backupFull = new BackupFull();
        List<String> includePaths = new ArrayList<>();
        includePaths.add("data");
        includePaths.add("chain_data");
        backupFull.setIncludePaths(includePaths);
        BackupDataType backupDataType = new BackupDataType();
        backupDataType.setFull(backupFull);

        // backend
        Backend backend = new Backend();
        backend.setRepoPasswordSecretRef(new RepoPasswordSecret("backup-repo", "password"));
        BackendLocal backendLocal = new BackendLocal("/hello", "nas-client-provisioner");
        backend.setLocal(backendLocal);

        // backupParam
        BackupParam backupParam = BackupParam.Builder.builder()
                .namespace("zhujq")
                .chain("test-chain-zenoh-overlord")
                .node("test-chain-zenoh-overlord-node0")
                .name("full-backup-to-local-1")
                .deployMethod("cloud-config")
                .dataType(backupDataType)
                .backend(backend)
                .build();

        System.out.println("param: " + backupParam.toJson());
        try {
            initApiClient(null);
            backup(backupParam);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRestore() {
        Gson gson = new Gson();
        // backend
        Backend backend = new Backend();
        backend.setRepoPasswordSecretRef(new RepoPasswordSecret("backup-repo", "password"));
        BackendLocal backendLocal = new BackendLocal("/hello", "nas-client-provisioner");
        backend.setLocal(backendLocal);

        // backupParam
        RestoreParam restoreParam = RestoreParam.Builder.builder()
                .namespace("zhujq")
                .chain("test-chain-zenoh-overlord")
                .node("test-chain-zenoh-overlord-node0")
                .name("full-restore-from-local")
                .deployMethod("cloud-config")
                .backup("full-backup-to-local")
                .backend(backend)
                .build();

        System.out.println("param: " + gson.toJson(restoreParam));
        try {
            initApiClient(null);
            restore(restoreParam);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
            throw new RuntimeException(e);
        }
    }
}