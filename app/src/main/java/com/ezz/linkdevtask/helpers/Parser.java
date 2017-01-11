package com.ezz.linkdevtask.helpers;

import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.models.NewsData;
import com.ezz.linkdevtask.models.NewsDetail;
import com.ezz.linkdevtask.models.NewsItem;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ahmed.Ezz on 1/4/2017.
 */

public class Parser {
    public static ArrayList<AllNews> getAllNews(String response)
    {
        Gson g = new Gson();
        return (ArrayList<AllNews>) g.fromJson(response,NewsData.class).getNews();
    }
    public static NewsItem getNewsItemDetails(String response)
    {
        Gson g = new Gson();
        return  g.fromJson(response,NewsDetail.class).getNewsItem();
    }
}
