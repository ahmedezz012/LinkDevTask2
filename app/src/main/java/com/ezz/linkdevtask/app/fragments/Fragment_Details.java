package com.ezz.linkdevtask.app.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.helpers.Parser;
import com.ezz.linkdevtask.helpers.Services;
import com.ezz.linkdevtask.app.MyApplication;
import com.ezz.linkdevtask.helpers.Utilities;
import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.models.NewsItem;
import com.ezz.linkdevtask.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Details extends Fragment {
    private AllNews news;
    ImageView imgnewsImage;
    RelativeLayout rlprogressbar;
    TextView txtLikes,txtViews,txtDate,txtdescription, txttitle;
    Services services;
    private String currentnewkey="currentnew";
    public Fragment_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details_details, container, false);
        rlprogressbar = (RelativeLayout) v.findViewById(R.id.load_layout);
        imgnewsImage = (ImageView) v.findViewById(R.id.imgnewsImage);
        txtLikes = (TextView) v.findViewById(R.id.txtLikes);
        txtViews = (TextView) v.findViewById(R.id.txtViews);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        txtdescription = (TextView) v.findViewById(R.id.txtdescription);
        txttitle = (TextView) v.findViewById(R.id.txttitle);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            news = (AllNews) savedInstanceState.getSerializable(currentnewkey);
        }
        services=Services.getInstance(getActivity());
    }
    @Override
    public void onStart() {
        super.onStart();
        getNewData();
    }

    public void getNewData()
    {
        BindData(news);
        rlprogressbar.setVisibility(View.VISIBLE);
        if(Utilities.CheckIfApplicationIsConnected(getActivity())) {
            try {
                services.makeRequestTogetNewDetails(news.getNid(), response, error);
            }catch (Exception ex)
            {
                Toast.makeText(getActivity(),getString(R.string.error),Toast.LENGTH_LONG).show();
            }
        }
        else
            Utilities.NetworkError(rlprogressbar,getActivity());
    }
    public void setNews(AllNews news) {
        this.news = news;
    }
    void BindData(AllNews news)
    {
        txtLikes.setText(getString(R.string.likes)+" ("+news.getLikes()+")");
        SharedPreferences force_pref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String language = force_pref.getString(MyApplication.FORCE_LOCAL, "");
        if (language.equals(Constants.arabic))
        {
            txtViews.setText(getString(R.string.views)+" "+news.getNumofViews());
        }
        else
        {
            txtViews.setText(news.getNumofViews()+" "+getString(R.string.views));
        }
        txtDate.setText(news.getPostDate());
        txttitle.setText(news.getNewsTitle());
    }
    void BindData(NewsItem newsItem)
    {
        Picasso.with(getActivity()).load(newsItem.getImageUrl()).placeholder(R.drawable.news_image_placeholder).into(imgnewsImage);
        txtdescription.setText(newsItem.getItemDescription());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(currentnewkey,news);
    }
    Response.Listener<String> response = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            rlprogressbar.setVisibility(View.GONE);
            NewsItem newsItem = Parser.getNewsItemDetails(response);
            BindData(newsItem);
        }
    };
    Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Utilities.NetworkError(rlprogressbar,getActivity());
        }
    };
}
