package com.ezz.linkdevtask.app.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.ezz.linkdevtask.R;
import com.ezz.linkdevtask.helpers.GoogleServices;
import com.ezz.linkdevtask.helpers.SocialMediaDetector;
import com.ezz.linkdevtask.helpers.Utilities;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;


public class Activity_Login extends FragmentActivity {

    GoogleApiClient googleApiClient;
    GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__login);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.TWITTER_KEY), getString(R.string.TWITTER_SECRET));
        Fabric.with(this, new Twitter(authConfig));
        googleSignInOptions = GoogleServices.getgoogleSignInOptions();
        googleApiClient = GoogleServices.getgoogleApiClient(this, googleSignInOptions);
        SocialMediaDetector currentsocial = Utilities.getcurrentSocial(googleApiClient);
        if (currentsocial != SocialMediaDetector.nothing) {
            gotoNewsScreen();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentlogin);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    void gotoNewsScreen() {
        Intent intent = new Intent(Activity_Login.this, Activity_News.class);
        startActivity(intent);
        finish();
    }
}
