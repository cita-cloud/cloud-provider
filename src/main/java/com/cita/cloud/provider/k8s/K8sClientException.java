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

import java.util.Optional;

/**
 * @author FreeQ
 * @description <p>k8s调用相关的基础类，所有自定义k8s相关的异常类都需要继承本类</p>
 * @date 2023/1/16 9:45
 **/
public class K8sClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义详细异常信息
     */
    protected String detailMsg;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    public K8sClientException() {
        super("");
    }

    public K8sClientException(String detailMsg, Object... args) {
        super(Optional.ofNullable(detailMsg).orElse(""));
        this.args = args;
    }

    public K8sClientException(Throwable cause, String detailMsg, Object... args) {
        super(Optional.ofNullable(detailMsg).orElse(""), cause);
        this.args = args;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
