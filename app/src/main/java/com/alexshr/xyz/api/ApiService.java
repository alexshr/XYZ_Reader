package com.alexshr.xyz.api;

import com.alexshr.xyz.data.Article;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

import static com.alexshr.xyz.AppConfig.API_PATH;

public interface ApiService {

    @GET(API_PATH)
    Observable<Response<List<Article>>> getArticlesObservable();
}
