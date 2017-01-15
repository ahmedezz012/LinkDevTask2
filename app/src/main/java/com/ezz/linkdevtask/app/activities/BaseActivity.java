package com.ezz.linkdevtask.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

/**
 * Created by Ahmed.Ezz on 1/5/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected void setToolBar(Toolbar toolbar, String title) {
        if (toolbar != null && !TextUtils.isEmpty(title)) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

}
