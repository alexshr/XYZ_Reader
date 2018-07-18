package com.alexshr.xyz.ui;

import android.arch.lifecycle.LiveData;

import com.alexshr.xyz.api.AppRepository;
import com.alexshr.xyz.data.Article;
import com.alexshr.xyz.viewmodel.AbstractViewModel;

import java.util.List;

import javax.inject.Inject;

public class ListActivityViewModel extends AbstractViewModel<List<Article>> {
    @Inject
    public ListActivityViewModel(AppRepository repository) {
        super(repository);
    }

    public LiveData<List<Article>> getArticles() {
        if (getLiveData() == null) setLiveData(repository.getDao().getArticles());
        return getLiveData();
    }
}
