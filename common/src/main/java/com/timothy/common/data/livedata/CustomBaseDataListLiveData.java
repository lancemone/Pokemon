package com.timothy.common.data.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.timothy.common.data.base.BaseData;

import java.util.Collections;
import java.util.List;

public class CustomBaseDataListLiveData<T extends BaseData> extends LiveData<List<T>> {

    public CustomBaseDataListLiveData(List<T> value) {
        super(value);
    }

    public CustomBaseDataListLiveData() {
        super();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<T>> observer) {
        super.observe(owner, observer);
    }

    @Override
    public void observeForever(@NonNull Observer<? super List<T>> observer) {
        super.observeForever(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super List<T>> observer) {
        super.removeObserver(observer);
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
    }

    @Override
    public void postValue(List<T> value) {
        super.postValue(value);
    }

    @Override
    public void setValue(List<T> value) {
        super.setValue(value);
    }

    @NonNull
    @Override
    public List<T> getValue() {
        List<T> object = super.getValue();
        if (object == null){
            object = Collections.emptyList();
        }
        return object;
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
