package com.cita;

import com.cita.provider.param.BackupFull;
import com.cita.provider.param.BackupParam;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BackupParam backupParam = new BackupParam();
        BackupFull backupDataType = new BackupFull();
        backupDataType.setIncludePaths(new ArrayList<>());
        backupParam.setDataType(backupDataType);
        System.out.println(backupParam.toJson());
    }
}