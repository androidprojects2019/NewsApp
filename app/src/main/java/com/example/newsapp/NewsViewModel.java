package com.example.newsapp;

import com.example.newsapp.apis.model.ArticlesItem;
import com.example.newsapp.apis.model.SourcesItem;

import java.util.List;

import androidx.databinding.ObservableField ;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class NewsViewModel extends ViewModel {

    MutableLiveData<List<SourcesItem>> sourcesList
            = new MutableLiveData<>();
    MutableLiveData<List<ArticlesItem>> newsList =new MutableLiveData<>();
    MutableLiveData<Integer> messageData=new MutableLiveData<>();
    public ObservableField<String> searchText =new ObservableField<>("");
    public MutableLiveData<String> searchMutableLiveData =new MutableLiveData<>();
    NewsRepository newsRepository =new NewsRepository();

    public NewsViewModel(){
        sourcesList = newsRepository.sourcesList;
        newsList = newsRepository.newsList;
        messageData = newsRepository.messageData;
    }

    public void getNewsSource(){
        newsRepository.getNewsSources();
    }

    public void getNewsBySourceId(String sourceId){
        newsRepository.getNewsBySourceId(sourceId);
    }


    public void search() {
        newsRepository.search(searchText.get());
    }

}















