package com.timothy.common.data.base;

import androidx.annotation.NonNull;

public interface BaseDataInterface {

    String getId();

    int getType();

    public <T extends BaseDataInterface>boolean hasChange(@NonNull T object);
}
