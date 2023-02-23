package com.cita.provider.param;

public class BackupDataType {
    BackupFull full;
    BackupState state;

    public BackupDataType() {
    }

    public BackupDataType(BackupFull full, BackupState state) {
        this.full = full;
        this.state = state;
    }

    public BackupFull getFull() {
        return full;
    }

    public void setFull(BackupFull full) {
        this.full = full;
    }

    public BackupState getState() {
        return state;
    }

    public void setState(BackupState state) {
        this.state = state;
    }
}

