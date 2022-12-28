package com.timothy.common.data.base;

public enum DataStatus {

    START(-1, "start"),
    SUCCESS(100, "success"),
    EMPTY_DATA(101, "empty"),
    NET_ERROR(102, "network_error");

    private int code;
    private String msg;

    private DataStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
