package com.cita;

import com.cita.provider.param.*;
import io.kubernetes.client.openapi.ApiException;

import java.util.ArrayList;
import java.util.List;

import static com.cita.provider.Provider.backup;
import static com.cita.provider.k8s.K8sInvokerClient.initApiClient;

public class Main {
    public static void main(String[] args) {
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
                .name("full-backup-to-local")
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
}