package com.timothy.common.data.base;

public enum DataType {

    NONE(0),
    IMAGE_FEED(101),

    // tradition
    TRADITION_COLOR(201);

    private final int typeCode;

    private DataType(int code){
        typeCode = code;
    }

    public int getType() {
        return typeCode;
    }
}
