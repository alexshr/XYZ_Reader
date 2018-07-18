package com.alexshr.xyz.ui;

import android.arch.lifecycle.LiveData;

import com.alexshr.xyz.api.AppRepository;
import com.alexshr.xyz.viewmodel.AbstractViewModel;

import javax.inject.Inject;

public class DetailActivityViewModel extends AbstractViewModel<Integer> {

    @Inject
    public DetailActivityViewModel(AppRepository repository) {
        super(repository);
    }

    public LiveData<Integer> getArticlesCount() {
        if (getLiveData() == null) setLiveData(repository.getDao().getArticlesAmount());
        return getLiveData();
    }
}
