package com.alexshr.xyz.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alexshr.xyz.api.AppRepository;

import timber.log.Timber;

public abstract class AbstractViewModel<R> extends ViewModel {

    protected AppRepository repository;

    private LiveData<R> liveData;
    private MutableLiveData<Throwable> errorData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNewData = new MutableLiveData<>();

    public AbstractViewModel(AppRepository repository) {
        this.repository = repository;
        progressData.postValue(false);
    }

    public void requestApi() {
        progressData.postValue(true);
        isNewData.postValue(false);
        repository.getApiObservable()
                .doOnNext(list -> {
                    if (list.size() > 0) {
                        isNewData.postValue(true);
                    }
                })
                .doOnError(errorData::postValue)
                .doFinally(() -> progressData.postValue(false))
                .subscribe();
    }

    public MutableLiveData<Throwable> getErrorData() {
        return errorData;
    }

    public MutableLiveData<Boolean> getProgressData() {
        return progressData;
    }

    protected LiveData<R> getLiveData() {
        return liveData;
    }

    protected void setLiveData(LiveData<R> data) {
        this.liveData = data;
        data.observeForever(obj -> Timber.d("test data observer: %s", data.getValue()));
    }

    public MutableLiveData<Boolean> getNewData() {
        return isNewData;
    }
}
