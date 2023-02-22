package com.cita.provider.param;

public class BackupState extends BackupDataType {
    // 块高
    long blockHeight;

    public BackupState() {
        super(Type.STATE);
    }

    public BackupState(long blockHeight) {
        super(Type.STATE);
        this.blockHeight = blockHeight;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }
}
