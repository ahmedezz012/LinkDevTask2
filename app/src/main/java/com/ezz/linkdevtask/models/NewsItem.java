
package com.ezz.linkdevtask.models;


public class NewsItem {

    private String NewsTitle;
    private String ItemDescription;
    private String Nid;
    private String NumofViews;
    private String Likes;
    private String PostDate;
    private String ImageUrl;
    private String NewsType;
    private String ShareURL;
    private String VideoURL;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
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

    public String getShareURL() {
        return ShareURL;
    }

    public void setShareURL(String shareURL) {
        ShareURL = shareURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
    }
}
