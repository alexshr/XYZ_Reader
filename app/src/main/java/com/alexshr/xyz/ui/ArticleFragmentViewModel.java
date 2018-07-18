package com.alexshr.xyz.ui;

import android.arch.lifecycle.LiveData;

import com.alexshr.xyz.api.AppRepository;
import com.alexshr.xyz.data.Article;
import com.alexshr.xyz.viewmodel.AbstractViewModel;

import javax.inject.Inject;

public class ArticleFragmentViewModel extends AbstractViewModel<Article> {

    @Inject
    public ArticleFragmentViewModel(AppRepository repository) {
        super(repository);
    }

    public LiveData<Article> getArticle(int pos) {
        if (getLiveData() == null) setLiveData(repository.getDao().getArticleByPosition(pos));

        return getLiveData();
    }
}
