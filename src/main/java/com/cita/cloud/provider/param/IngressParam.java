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

import io.kubernetes.client.openapi.models.V1IngressRule;
import io.kubernetes.client.openapi.models.V1IngressTLS;

import java.util.List;
import java.util.Map;

public class IngressParam {
    String name;
    String namespace;
    String nodeName;
    Map<String, String> annotations;
    List<V1IngressRule> rules;
    List<V1IngressTLS> tls;

    public IngressParam() {
    }

    public IngressParam(String name, String namespace, String nodeName, Map<String, String> annotations, List<V1IngressRule> rules, List<V1IngressTLS> tls) {
        this.name = name;
        this.namespace = namespace;
        this.nodeName = nodeName;
        this.annotations = annotations;
        this.rules = rules;
        this.tls = tls;
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

    public Map<String, String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    public List<V1IngressRule> getRules() {
        return rules;
    }

    public void setRules(List<V1IngressRule> rules) {
        this.rules = rules;
    }

    public List<V1IngressTLS> getTls() {
        return tls;
    }

    public void setTls(List<V1IngressTLS> tls) {
        this.tls = tls;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private String nodeName;
        private Map<String, String> annotations;
        private List<V1IngressRule> rules;
        private List<V1IngressTLS> tls;

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

        public Builder annotations(Map<String, String> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder rules(List<V1IngressRule> rules) {
            this.rules = rules;
            return this;
        }

        public Builder tls(List<V1IngressTLS> tls) {
            this.tls = tls;
            return this;
        }

        public IngressParam build() {
            IngressParam ingressParam = new IngressParam();
            ingressParam.setName(name);
            ingressParam.setNamespace(namespace);
            ingressParam.setNodeName(nodeName);
            ingressParam.setAnnotations(annotations);
            ingressParam.setRules(rules);
            ingressParam.setTls(tls);
            return ingressParam;
        }
    }
}
