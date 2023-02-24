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

public class Backend {
    // 备份仓库对应的secret
    RepoPasswordSecret repoPasswordSecretRef;

    // 后端存储的类型：local/s3
    BackendLocal local;
    BackendS3 s3;

    public Backend() {
    }

    public Backend(RepoPasswordSecret repoPasswordSecretRef, BackendLocal local, BackendS3 s3) {
        this.repoPasswordSecretRef = repoPasswordSecretRef;
        this.local = local;
        this.s3 = s3;
    }

    public RepoPasswordSecret getRepoPasswordSecretRef() {
        return repoPasswordSecretRef;
    }

    public void setRepoPasswordSecretRef(RepoPasswordSecret repoPasswordSecretRef) {
        this.repoPasswordSecretRef = repoPasswordSecretRef;
    }

    public BackendLocal getLocal() {
        return local;
    }

    public void setLocal(BackendLocal local) {
        this.local = local;
    }

    public BackendS3 getS3() {
        return s3;
    }

    public void setS3(BackendS3 s3) {
        this.s3 = s3;
    }
}
