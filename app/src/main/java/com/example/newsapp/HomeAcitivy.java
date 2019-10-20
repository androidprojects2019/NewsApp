package com.example.newsapp;

import com.example.newsapp.Base.BaseActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.apis.model.ArticlesItem;

import com.example.newsapp.apis.model.SourcesItem;

import android.os.Bundle;

import com.example.newsapp.databinding.ActivityHomeAcitivyBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class HomeAcitivy extends BaseActivity<ActivityHomeAcitivyBinding,
        NewsViewModel> {

    RecyclerView.LayoutManager layoutManager;
    NewsRecyclerAdapter adapter;
    NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_news);

        newsViewModel = ViewModelProviders.of(this)
                .get(NewsViewModel.class);
        databinding.setVm(viewModel);
        initRecyclerView();
        observeLiveData();
        newsViewModel.getNewsSource();


    }

    private void observeLiveData() {
        newsViewModel.sourcesList.observe(this,
                new Observer<List<SourcesItem>>() {
                    @Override
                    public void onChanged(List<SourcesItem> sourcesItems) {
                        showNewsSourcesInTab(sourcesItems);
                    }
                });
        newsViewModel.newsList.observe(this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                adapter.changeData(articlesItems);
            }
        });
        newsViewModel.messageData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer messageId) {
                showMessage(messageId, R.string.ok);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_acitivy;
    }

    @Override
    protected NewsViewModel getViewModel() {
        return ViewModelProviders.of(this).get(NewsViewModel.class);
    }

    private void initRecyclerView() {
        adapter = new NewsRecyclerAdapter(null);
        layoutManager = new LinearLayoutManager(this);
        databinding.recyclerView.setAdapter(adapter);
        databinding.recyclerView.setLayoutManager(layoutManager);
    }


    private void showNewsSourcesInTab(List<SourcesItem> newsSources) {
        for (SourcesItem source : newsSources) {
            TabLayout.Tab tab = databinding.tablayout.newTab();
            tab.setText(source.getName());
            tab.setTag(source);
            databinding.tablayout.addTab(tab);
        }
        databinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem source = ((SourcesItem) tab.getTag());
                newsViewModel.getNewsBySourceId(source.getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem source = ((SourcesItem) tab.getTag());
                newsViewModel.getNewsBySourceId(source.getId());

            }
        });
        databinding.tablayout.getTabAt(0).select();
    }

}
