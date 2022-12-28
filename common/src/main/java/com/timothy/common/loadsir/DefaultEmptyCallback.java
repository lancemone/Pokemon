package com.timothy.common.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.timothy.common.R;

public class DefaultEmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_default_empty;
    }
}
