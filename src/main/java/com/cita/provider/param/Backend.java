package com.cita.provider.param;


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
