package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsapp.apis.model.ArticlesItem;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.viewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    List<ArticlesItem> newsList;

    public NewsRecyclerAdapter(List<ArticlesItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ArticlesItem newsItem = newsList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.source_name.setText(newsItem.getSource().getName());
        holder.date.setText(newsItem.getPublishedAt());
        holder.desc.setText(newsItem.getDescription());
        Glide.with(holder.itemView).load(newsItem.getUrlToImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (newsList == null) return 0;
        return newsList.size();
    }

    public void changeData(List<ArticlesItem> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView title, source_name, date, desc;
        ImageView image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            source_name = itemView.findViewById(R.id.source_name);
            date = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image);
        }
    }
}
