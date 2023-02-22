package com.cita.provider.param;

import java.util.List;

public class BackupFull extends BackupDataType {
    // 需要备份的目录列表
    List<String> includePaths;

    public BackupFull() {
        super(Type.FULL);
    }

    public BackupFull(List<String> includePaths) {
        super(Type.FULL);
        this.includePaths = includePaths;
    }

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = includePaths;
    }
}
