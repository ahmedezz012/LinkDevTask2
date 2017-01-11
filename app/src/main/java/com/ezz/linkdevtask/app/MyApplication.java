package com.ezz.linkdevtask.app;

import com.ezz.linkdevtask.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MyApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    public static final String FORCE_LOCAL = "force_local";
    public static final String FORCE_CURRENCY = "force_currency";

    @Override
    public void onCreate() {
        updateLanguage(this, null);
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static void updateLanguage(Context ctx, String lang) {
        Configuration cfg = new Configuration();
        SharedPreferences force_pref = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String language = force_pref.getString(FORCE_LOCAL, "");

        if (TextUtils.isEmpty(language) && lang == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cfg.setLocale(Locale.getDefault()) ;
            }

            SharedPreferences.Editor edit = force_pref.edit();
            String tmp = "";
            tmp = Locale.getDefault().toString().substring(0, 2);

            edit.putString(FORCE_LOCAL, tmp);
            edit.commit();

        } else if (lang != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cfg.setLocale(new Locale(lang));
            }
            SharedPreferences.Editor edit = force_pref.edit();
            edit.putString(FORCE_LOCAL, lang);
            edit.commit();

        } else if (!TextUtils.isEmpty(language)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cfg.setLocale(new Locale(language));
            }
        }

        ctx.getResources().getConfiguration().setTo(cfg);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        SharedPreferences force_pref = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext()
                        .getApplicationContext());

        String language = force_pref.getString(FORCE_LOCAL, "");

        super.onConfigurationChanged(newConfig);
        updateLanguage(this, language);
    }
}
