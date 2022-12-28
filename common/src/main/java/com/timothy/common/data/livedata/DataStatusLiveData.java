package com.timothy.common.data.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.timothy.common.data.base.DataStatus;

public class DataStatusLiveData extends LiveData<DataStatus> {

    public DataStatusLiveData(DataStatus value) {
        super(value);
    }

    public DataStatusLiveData() {
        super();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super DataStatus> observer) {
        super.observe(owner, observer);
    }

    @Override
    public void observeForever(@NonNull Observer<? super DataStatus> observer) {
        super.observeForever(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super DataStatus> observer) {
        super.removeObserver(observer);
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
    }

    @Override
    public void postValue(DataStatus value) {
        super.postValue(value);
    }

    @Override
    public void setValue(DataStatus value) {
        super.setValue(value);
    }

    @Nullable
    @Override
    public DataStatus getValue() {
        return super.getValue();
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    @Override
    public boolean hasObservers() {
        return super.hasObservers();
    }

    @Override
    public boolean hasActiveObservers() {
        return super.hasActiveObservers();
    }
}
