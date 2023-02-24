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

package com.cita.cloud.provider.k8s;

/**
 * k8s操作客户端初始化异常
 *
 * @author : FreeQ
 * @version : 1.0
 * @date : 2023/1/16 9:46
 **/
public class K8sClientInitException extends K8sClientException {

    private static final long serialVersionUID = 1L;

    public K8sClientInitException() {
        super();
    }

    public K8sClientInitException(String detailMsg) {
        super(detailMsg);
    }

    public K8sClientInitException(String detailMsg, Object... args) {
        super(detailMsg, args);
    }

    public K8sClientInitException(Throwable cause, String detailMsg, Object... args) {
        super(cause, detailMsg, args);
    }


}
