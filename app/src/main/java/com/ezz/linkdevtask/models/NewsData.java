package com.ezz.linkdevtask.models;

import java.util.List;

public class NewsData {

    private List<AllNews> News = null;


    public NewsData() {
    }

    public NewsData(List<AllNews> News) {
        super();
        this.News = News;
    }


    public List<AllNews> getNews() {
        return News;
    }


    public void setNews(List<AllNews> news) {
        this.News = news;
    }

}
