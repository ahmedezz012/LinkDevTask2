package com.ezz.linkdevtask.app.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezz.linkdevtask.adapters.RecyclerView_News_Adapter;
import com.ezz.linkdevtask.helpers.Parser;
import com.ezz.linkdevtask.helpers.Services;
import com.ezz.linkdevtask.helpers.Utilities;
import com.ezz.linkdevtask.listeners.NewSelectedListener;
import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_news extends Fragment {


    ArrayList<AllNews> news;
    RecyclerView newslistRecyclerView;
    RecyclerView_News_Adapter recyclerViewNewsAdapter;
    Services services;
    NewSelectedListener newItemSelectedListener;
    RelativeLayout rlprogressbar;
    public Fragment_news() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.newItemSelectedListener = (NewSelectedListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_news, container, false);
        rlprogressbar = (RelativeLayout) v.findViewById(R.id.load_layout);
        newslistRecyclerView = (RecyclerView) v.findViewById(R.id.newslistRecyclerView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        news = new ArrayList<>();
        recyclerViewNewsAdapter = new RecyclerView_News_Adapter(getActivity(),news,newItemSelectedListener);
        newslistRecyclerView.setAdapter(recyclerViewNewsAdapter);
        newslistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        services = Services.getInstance(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataRequest();
    }

    public void getDataRequest()
    {
        rlprogressbar.setVisibility(View.VISIBLE);
        if(Utilities.CheckIfApplicationIsConnected(getActivity())) {
            try {
                services.makeRequestTogetListNews(response, error);
            }catch (Exception exc)
            {
                Toast.makeText(getActivity(),getString(R.string.error),Toast.LENGTH_LONG).show();
            }
        }
        else
            Utilities.NetworkError(rlprogressbar,getActivity());

    }

    Response.Listener<String> response = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            rlprogressbar.setVisibility(View.GONE);
            news = Parser.getAllNews(response);
            recyclerViewNewsAdapter.setArrayList(news);
        }
    };

    Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Utilities.NetworkError(rlprogressbar,getActivity());
        }
    };

}
