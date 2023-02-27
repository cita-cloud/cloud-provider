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
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1ServicePort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cita.cloud.provider.Provider.*;
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
        // 集群中的首次备份需要先 createSecret
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
        }
    }

    @Test
    void testCreateBackupAndRestoreSecret() {
        // backup-repo
        Map<String, String> data = new HashMap<>();
        data.put("password", "p@ssw0rd");
        SecretParam secretParam = SecretParam.Builder.builder()
                .namespace("zhujq")
                .name("backup-repo")
                .stringData(data)
                .build();

        Gson gson = new Gson();
        System.out.println("param: " + gson.toJson(secretParam));
        try {
            initApiClient(null);
            createSecret(secretParam);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }

        // minio-credentials
        Map<String, String> data1 = new HashMap<>();
        data.put("username", "minio");
        data.put("password", "minio123");
        SecretParam secretParam1 = SecretParam.Builder.builder()
                .namespace("zhujq")
                .name("minio-credentials")
                .stringData(data1)
                .build();

        System.out.println("param: " + gson.toJson(secretParam));
        try {
            createSecret(secretParam1);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }
    }

    @Test
    void testBlockHeightFallback() {
        Gson gson = new Gson();

        // backupParam
        BlockHeightFallbackParam blockHeightFallbackParam = BlockHeightFallbackParam.Builder.builder()
                .namespace("zhujq")
                .chain("test-chain-zenoh-overlord")
                .node("test-chain-zenoh-overlord-node2")
                .name("fallback-sample-1")
                .deployMethod("cloud-config")
                .blockHeight(1000)
                .build();

        System.out.println("param: " + gson.toJson(blockHeightFallbackParam));
        try {
            initApiClient(null);
            blockHeightFallback(blockHeightFallbackParam);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }
    }

    @Test
    void testLoadBalancer() {
        // alicloud
        Map<String, String> annotations = new HashMap<>();
        annotations.put("service.beta.kubernetes.io/alibaba-cloud-loadbalancer-health-check-interval", "50");
        annotations.put("service.beta.kubernetes.io/alibaba-cloud-loadbalancer-id", "lb-g2736gfq5f34bfq234gq3");
        annotations.put("service.beta.kubernetes.io/alicloud-loadbalancer-force-override-listeners", "true");
        List<V1ServicePort> ports = new ArrayList<>();
        V1ServicePort port = new V1ServicePort();
        port.setName("network");
        port.setPort(30040);
        port.setProtocol("TCP");
        port.setTargetPort(new IntOrString(40000));
        ports.add(port);

        LoadBalancerParam param = LoadBalancerParam.Builder.builder()
                .namespace("zhujq")
                .name("chain-982591827458984134-3-network")
                .nodeName("chain-982591827458984134-3")
                .annotations(annotations)
                .ports(ports)
                .build();

        // huaweicloud
//        Map<String, String> annotations = new HashMap<>();
//        annotations.put("kubernetes.io/elb.health-check-flag", "off");
//        annotations.put("kubernetes.io/elb.id", "a06g378d-693c-4618-90ce-c7921b270ed8");
//        annotations.put("kubernetes.io/elb.pass-through", "true");
//        List<V1ServicePort> ports = new ArrayList<>();
//        V1ServicePort port = new V1ServicePort();
//        port.setName("network");
//        port.setPort(30430);
//        port.setProtocol("TCP");
//        port.setTargetPort(new IntOrString(40000));
//        ports.add(port);
//
//        LoadBalancerParam param = LoadBalancerParam.Builder.builder()
//                .namespace("zhujq")
//                .name("chain-718354812389371465-3-network")
//                .nodeName("chain-718354812389371465-3")
//                .annotations(annotations)
//                .ports(ports)
//                .build();

        // metallb
//        Map<String, String> annotations = new HashMap<>();
//        annotations.put("metallb.universe.tf/allow-shared-ip", "cita-cloud");
//        List<V1ServicePort> ports = new ArrayList<>();
//        V1ServicePort port = new V1ServicePort();
//        port.setName("network");
//        port.setPort(30130);
//        port.setNodePort(32518);
//        port.setProtocol("TCP");
//        port.setTargetPort(new IntOrString(40000));
//        ports.add(port);
//
//        LoadBalancerParam param = LoadBalancerParam.Builder.builder()
//                .namespace("zhujq")
//                .name("chain-718354812389371465-1-network")
//                .nodeName("chain-718354812389371465-1")
//                .annotations(annotations)
//                .loadBalancerIP("191.168.121.2")
//                .ports(ports)
//                .build();

        Gson gson = new Gson();
        System.out.println("param: " + gson.toJson(param));
        try {
            initApiClient(null);
            loadBalancer(param);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }
    }

    @Test
    void testCreateIngressSecret() {
        // ca
        Map<String, String> data = new HashMap<>();
        data.put("ca.crt", "LS0tLS1CR...");
        SecretParam secretParam = SecretParam.Builder.builder()
                .namespace("zhujq")
                .name("chain-826454031529545728-ca")
                .stringData(data)
                .build();

        Gson gson = new Gson();
        System.out.println("param: " + gson.toJson(secretParam));
        try {
            initApiClient(null);
            createSecret(secretParam);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }

        // tls

        Map<String, String> data1 = new HashMap<>();
        data.put("tls.crt", "LS0tLS1CRUdJTi...");
        data.put("tls.key", "LS0tLS1CRUdJTi...");
        SecretParam secretParam1 = SecretParam.Builder.builder()
                .namespace("zhujq")
                .name("all-chain-826454031529545728-0-tls")
                .stringData(data1)
                .build();

        System.out.println("param: " + gson.toJson(secretParam1));
        try {
            createSecret(secretParam1);
        } catch (ApiException e) {
            System.out.println("Response: " + e.getResponseBody());
        }
    }
}
