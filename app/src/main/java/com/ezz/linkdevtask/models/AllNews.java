
package com.ezz.linkdevtask.models;


import java.io.Serializable;

public class AllNews implements Serializable{

    private String NewsTitle;
    private String Nid;
    private String PostDate;
    private String ImageUrl;
    private String NewsType;
    private String NumofViews;
    private String Likes;


    public AllNews() {
    }


    public AllNews(String nid, String newsTitle, String likes, String imageUrl, String newsType, String numofViews, String postDate) {
        Nid = nid;
        NewsTitle = newsTitle;
        Likes = likes;
        ImageUrl = imageUrl;
        NewsType = newsType;
        NumofViews = numofViews;
        PostDate = postDate;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsType() {
        return NewsType;
    }

    public void setNewsType(String newsType) {
        NewsType = newsType;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getNumofViews() {
        return NumofViews;
    }

    public void setNumofViews(String numofViews) {
        NumofViews = numofViews;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }
}
