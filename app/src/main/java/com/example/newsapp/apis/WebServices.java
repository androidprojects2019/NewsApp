package com.example.newsapp.apis;
import com.example.newsapp.apis.model.NewsCategoryResponse;
import com.example.newsapp.apis.model.NewsResponse;
import com.example.newsapp.apis.model.NewsSourcesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {
    @GET("sources")
    Single<NewsSourcesResponse> getNewsSources(@Query("apiKey") String key,
                                               @Query("language") String lang);

    @GET("everything")
    Single<NewsResponse> getNews(@Query("apiKey") String key,
                                 @Query("language") String lang,
                                 @Query("sources") String sources);
    @GET("everything")
    Single<NewsResponse> searchNews(@Query("apiKey") String key,
                                    @Query("language") String lang,
                                    @Query("qInTitle") String query);
 }