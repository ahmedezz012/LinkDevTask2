package com.ezz.linkdevtask.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezz.linkdevtask.adapters.Navigation_Listview_Adapter;
import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.app.fragments.Fragment_Details;
import com.ezz.linkdevtask.helpers.GoogleServices;
import com.ezz.linkdevtask.helpers.Utilities;
import com.ezz.linkdevtask.listeners.NewSelectedListener;
import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;


public class Activity_News extends BaseActivity implements NewSelectedListener{


    DrawerLayout navdrawer;
    Toolbar maintoolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView lstnavitems;
    TextView txtusername,txtusermail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorsharedPrefrences;
    Button btnlogout;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions googleSignInOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editorsharedPrefrences = sharedPreferences.edit();

        googleSignInOptions = GoogleServices.getgoogleSignInOptions();
        googleApiClient=GoogleServices.getgoogleApiClient(Activity_News.this,googleSignInOptions);

        Init_UI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (navdrawer.isDrawerOpen(GravityCompat.START)) {
            navdrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void Init_UI()
    {
        txtusername = (TextView) findViewById(R.id.txtusername);
        txtusermail = (TextView) findViewById(R.id.txtusermail);

        txtusername.setText(sharedPreferences.getString(Constants.nameprefkey,""));
        txtusermail.setText(sharedPreferences.getString(Constants.emailprefkey,""));

        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(btnlogoutonClickListener);

        maintoolbar = (Toolbar) findViewById(R.id.maintoolbar);
        setToolBar(maintoolbar,getString(R.string.News));
        lstnavitems = (RecyclerView) findViewById(R.id.lstnavitems);
        lstnavitems.setAdapter(new Navigation_Listview_Adapter(this));
        lstnavitems.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        navdrawer = (DrawerLayout) findViewById(R.id.navdrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,navdrawer,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        navdrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void NewSelected(AllNews selectedNew) {
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if(tabletSize){
            Fragment_Details fragment_details = new Fragment_Details();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragment_details.setNews(selectedNew);
            fragmentTransaction.replace(R.id.fragment_main_details_container,fragment_details);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }else {
            Intent intent = new Intent(Activity_News.this, Activity_Details.class);
            intent.putExtra(Constants.intentkey, selectedNew);
            startActivity(intent);
        }
    }
    View.OnClickListener btnlogoutonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            char currentsocial= Utilities.getcurrentSocial(googleApiClient);
            if(currentsocial=='f')
            {
                LoginManager.getInstance().logOut();
            }
            else if(currentsocial=='t') {
                Twitter.getInstance();
                Twitter.logOut();
            }
            else if(currentsocial=='g')
            {
                Auth.GoogleSignInApi.signOut(googleApiClient);
            }
            startActivity(new Intent(Activity_News.this,Activity_Login.class));
            finish();
            removeuserdatafromsharedpref();
        }
    };
    void removeuserdatafromsharedpref()
    {
        editorsharedPrefrences.remove(Constants.emailprefkey);
        editorsharedPrefrences.remove(Constants.nameprefkey);
        editorsharedPrefrences.commit();
    }
}
