package com.timothy.common.base;

import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import com.timothy.common.data.livedata.DataStatusLiveData;

abstract public class BaseViewModel extends ViewModel implements Observable {

    protected DataStatusLiveData dataStatusLiveData = new DataStatusLiveData();

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    protected abstract void initData();

    public DataStatusLiveData getDataStatus() {
        return dataStatusLiveData;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }
}
