package com.timothy.common.data.base;

import androidx.annotation.NonNull;

import java.util.Objects;

public abstract class BaseData implements BaseDataInterface {

    public static final int DEFAULT_DATA_TYPE = -99;

    @Override
    public <T extends BaseDataInterface> boolean hasChange(@NonNull T object) {
        return Objects.equals(object.getId(), getId()) || equals(object);
    }

    @Override
    public int getType() {
        // 默认返回None
        return DEFAULT_DATA_TYPE;
    }
}
