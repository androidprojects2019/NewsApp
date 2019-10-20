package com.example.newsapp;

import com.example.newsapp.apis.ApiManager;
import com.example.newsapp.apis.model.ArticlesItem;
import com.example.newsapp.apis.model.NewsResponse;
import com.example.newsapp.apis.model.NewsSourcesResponse;
import com.example.newsapp.apis.model.SourcesItem;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Nabil Mohamed on 10/19/2019.
 * m.nabil.fci2015@gmail.com
 */
public class NewsRepository {
    MutableLiveData<List<ArticlesItem>> newsList = new MutableLiveData<>();
    MutableLiveData<Integer> messageData = new MutableLiveData<>();
    MutableLiveData<List<SourcesItem>> sourcesList
            = new MutableLiveData<>();

    public void getNewsSources() {
        ApiManager.getApis()
                .getNewsSources(Constants.APIKEY, "en")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NewsSourcesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewsSourcesResponse newsSourcesResponse) {
                        sourcesList.setValue(newsSourcesResponse.getSources());

                    }

                    @Override
                    public void onError(Throwable e) {
                        messageData.setValue(R.string.somthing_went_wrong);

                    }
                });
    }

    public void getNewsBySourceId(String sourceId) {
        ApiManager.getApis()
                .getNews(Constants.APIKEY,
                        "en", sourceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        newsList.setValue(newsResponse.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        messageData.setValue(R.string.somthing_went_wrong);
                    }
                });



                /*.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call,
                                           Response<NewsResponse> response) {
                        newsList.setValue(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        messageData.setValue(R.string.somthing_went_wrong);
                    }
                });*/
    }

    public void search(String q) {
        ApiManager.getApis().searchNews(Constants.APIKEY, "en",
                q)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        newsList.setValue(newsResponse.getArticles());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
