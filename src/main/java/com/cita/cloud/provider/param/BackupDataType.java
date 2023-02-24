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

