package com.timothy.common.data.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.timothy.common.data.base.BaseData;

public class CustomBaseDataLiveData<T extends BaseData> extends LiveData<T> {

    public CustomBaseDataLiveData(T value) {
        super(value);
    }

    public CustomBaseDataLiveData() {
        super();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        super.observeForever(observer);
    }

    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        super.removeObserver(observer);
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        if (getValue() != null){
            if (!getValue().hasChange(value)){
                return;
            }
        }
        super.setValue(value);
    }

    @Nullable
    @Override
    public T getValue() {
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
