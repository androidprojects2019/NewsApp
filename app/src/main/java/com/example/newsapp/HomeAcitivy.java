package com.example.newsapp;

import Base.BaseActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apis.ApiManager;
import apis.model.ArticlesItem;
import apis.model.NewsResponse;
import apis.model.NewsSourcesResponse;
import apis.model.SourcesItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import java.util.List;


public class HomeAcitivy extends BaseActivity {

    TabLayout tabLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewsRecyclerAdapter adapter;
    public static final String APIKEY = "7b8aafaf16cb464e97e7bf64adba2386";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivy);
        tabLayout = findViewById(R.id.tablayout);
        recyclerView =findViewById(R.id.recycler_view);
        initRecyclerView();
        getNewsSources();

    }

    private void initRecyclerView() {
        adapter =new NewsRecyclerAdapter(null);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    List<SourcesItem> newsSources ;
    private void getNewsSources() {
        ApiManager.getApis()
                .getNewsSources(APIKEY,"en")
                .enqueue(new Callback<NewsSourcesResponse>() {
                    @Override
                    public void onResponse(Call<NewsSourcesResponse> call,
                                           Response<NewsSourcesResponse> response) {
                        if("ok".equals(response.body().getStatus())){
                            newsSources = response.body().getSources();
                            showNewsSourcesInTab();
                        }else {
                            showMessage(response.body().getMessage(),getString(R.string.ok));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsSourcesResponse> call,
                                          Throwable t) {
                        showMessage(t.getLocalizedMessage(),getString(R.string.ok));
                    }
                });
    }

    private void showNewsSourcesInTab() {
        for(SourcesItem source : newsSources){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(source.getName());
            tab.setTag(source);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem source = ((SourcesItem) tab.getTag());
                getNewsBySourceId(source.getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem source = ((SourcesItem) tab.getTag());
                getNewsBySourceId(source.getId());

            }
        });
        tabLayout.getTabAt(0).select();
    }



    private void getNewsBySourceId(String id) {
        ApiManager.getApis()
                .getNews(APIKEY,
                        "en",id)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call,
                                           Response<NewsResponse> response) {
                        if("ok".equals(response.body().getStatus())){
                            List<ArticlesItem> newsList = response.body().getArticles();
                            adapter.changeData(newsList);
                        }else {
                            showMessage(response.body().getMessage()
                                    ,getString(R.string.ok));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call,
                                          Throwable t) {
                        showMessage(t.getLocalizedMessage(),getString(R.string.ok));
                    }
                });
    }
}






