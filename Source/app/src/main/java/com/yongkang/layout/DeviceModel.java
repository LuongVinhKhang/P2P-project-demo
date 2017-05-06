package com.yongkang.layout;

/**
 * Created by Yong Kang on 07-May-17.
 */

public class DeviceModel {

    private Boolean connected;
    private String name;

    public DeviceModel(Boolean connected, String name) {
        this.connected = connected;
        this.name = name;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
