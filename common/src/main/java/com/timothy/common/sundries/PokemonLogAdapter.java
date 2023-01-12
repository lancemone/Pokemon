package com.timothy.common.sundries;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.timothy.common.constant.LoggerConstant;

public class PokemonLogAdapter extends AndroidLogAdapter {

    public PokemonLogAdapter() {
        this(PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(0)
                .methodOffset(0)
                .tag(LoggerConstant.DEFAULT_TAG)
                .build());
    }

    public PokemonLogAdapter(@NonNull FormatStrategy formatStrategy) {
        super(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return loggerFilter(priority, tag);
    }

    private boolean loggerFilter(int priority, @Nullable String tag) {
        return true;
    }
}
