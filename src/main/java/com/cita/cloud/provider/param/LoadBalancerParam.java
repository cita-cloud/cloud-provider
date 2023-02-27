package com.cita.cloud.provider.param;

import io.kubernetes.client.openapi.models.V1ServicePort;

import java.util.List;
import java.util.Map;

public class LoadBalancerParam {
    String name;
    String namespace;
    String nodeName;
    String loadBalancerIP;
    List<V1ServicePort> ports;
    Map<String, String> annotations;

    public LoadBalancerParam() {
    }

    public LoadBalancerParam(String name, String namespace, String nodeName, String loadBalancerIP, List<V1ServicePort> ports, Map<String, String> annotations) {
        this.name = name;
        this.namespace = namespace;
        this.nodeName = nodeName;
        this.loadBalancerIP = loadBalancerIP;
        this.ports = ports;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getLoadBalancerIP() {
        return loadBalancerIP;
    }

    public void setLoadBalancerIP(String loadBalancerIP) {
        this.loadBalancerIP = loadBalancerIP;
    }

    public List<V1ServicePort> getPorts() {
        return ports;
    }

    public void setPorts(List<V1ServicePort> ports) {
        this.ports = ports;
    }

    public Map<String, String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private String nodeName;
        private String loadBalancerIP;
        private List<V1ServicePort> ports;
        private Map<String, String> annotations;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder nodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder loadBalancerIP(String loadBalancerIP) {
            this.loadBalancerIP = loadBalancerIP;
            return this;
        }

        public Builder ports(List<V1ServicePort> ports) {
            this.ports = ports;
            return this;
        }

        public Builder annotations(Map<String, String> annotations) {
            this.annotations = annotations;
            return this;
        }

        public LoadBalancerParam build() {
            LoadBalancerParam loadBalancerParam = new LoadBalancerParam();
            loadBalancerParam.setName(name);
            loadBalancerParam.setNamespace(namespace);
            loadBalancerParam.setNodeName(nodeName);
            loadBalancerParam.setLoadBalancerIP(loadBalancerIP);
            loadBalancerParam.setPorts(ports);
            loadBalancerParam.setAnnotations(annotations);
            return loadBalancerParam;
        }
    }
}
