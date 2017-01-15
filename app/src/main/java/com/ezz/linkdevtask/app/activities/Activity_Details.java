package com.ezz.linkdevtask.app.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.app.fragments.Fragment_Details;
import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.R;

public class Activity_Details extends BaseActivity {

    Toolbar maintoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        InitUI();
        Fragment_Details fragment_details = (Fragment_Details) getSupportFragmentManager().findFragmentById(R.id.fragmentdetails);
        fragment_details.setNews((AllNews) getIntent().getSerializableExtra(Constants.intentkey));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void InitUI() {
        maintoolbar = (Toolbar) findViewById(R.id.maintoolbar);
        setToolBar(maintoolbar, getString(R.string.Newsdetails));
    }
}
