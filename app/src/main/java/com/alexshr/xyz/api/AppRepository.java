package com.alexshr.xyz.api;

import com.alexshr.xyz.data.Article;
import com.alexshr.xyz.db.AppDao;
import com.alexshr.xyz.rx.RestCallTransformer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class AppRepository {
    private ApiService apiService;
    private AppDao dao;
    private RestCallTransformer<List<Article>> transformer;

    @Inject
    public AppRepository(ApiService apiService, RestCallTransformer<List<Article>> transformer, AppDao dao) {
        this.apiService = apiService;
        this.dao = dao;
        this.transformer = transformer;
    }

    public Observable<List<Article>> getApiObservable() {
        return apiService.getArticlesObservable()
                .compose(transformer)
                .doOnNext(dao::insertArticles)
                .doOnNext(list -> {
                    for (Article article : list) Timber.d("%s", article);
                });
    }

    public AppDao getDao() {
        return dao;
    }
}
