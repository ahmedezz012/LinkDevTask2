package com.ezz.linkdevtask.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ezz.linkdevtask.R;
import com.ezz.linkdevtask.constants.Constants;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.twitter.sdk.android.Twitter;

public class Utilities {

    /**
     * global progress dialog
     */
    private static ProgressDialog progressDialog = null;

    /**
     * Dismiss progress dialog
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Show progress dialog
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showProgressDialog(Context context, String title,
                                          String message) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(context, title, message, true);
        progressDialog.setCancelable(true);
    }

    /**
     * Show progress dialog
     *
     * @param context
     * @param title
     * @param message
     * @param cancelable
     */

    public static void showProgressDialog(Context context, String title,
                                          String message, Boolean cancelable) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(context, title, message, true);
        progressDialog.setCancelable(cancelable);
    }

    /**
     * Check if Application is connected to the Internet
     *
     * @param context
     * @return
     */

    public static Boolean CheckIfApplicationIsConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            return activeNetInfo != null && activeNetInfo.isAvailable()
                    && activeNetInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * Show alert dialog
     *
     * @param context
     * @param msg
     * @param
     * @param btnStr
     */

    public static void ShowAlertDialoge(Context context, String msg,
                                        String btnStr) {

        Builder builder = new Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(btnStr,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    /**
     * Encode URl UTF-8
     *
     * @param originalUrl
     * @return
     * @throws UnsupportedEncodingException
     */

    public static String EncodeURL(String originalUrl)
            throws UnsupportedEncodingException {
        int lastSlashIndexLarge = originalUrl.lastIndexOf('/');
        String encodedUrl = originalUrl.substring(0, lastSlashIndexLarge + 1)
                + URLEncoder
                .encode(originalUrl.substring(lastSlashIndexLarge + 1,
                        originalUrl.length()), "UTF-8");
        String completeUrl = encodedUrl.replace("+", "%20");
        return completeUrl;
    }

    /**
     * Checks Email format
     *
     * @param email
     * @return
     */

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void NetworkError(RelativeLayout rlprogressbar, Context context) {
        if (rlprogressbar != null) {
            rlprogressbar.setVisibility(View.GONE);
        }
        Toast.makeText(context, context.getString(R.string.NetworkError), Toast.LENGTH_LONG).show();
    }

    /**
     * Convert from pixels to DP
     *
     * @param dp
     * @param context
     * @return
     */

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * Convert from Dp to pixels
     *
     * @param px
     * @param context
     * @return
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    // used to know what is current social media that the user logged in with it
    public static SocialMediaDetector getcurrentSocial(GoogleApiClient googleApiClient) {
        OptionalPendingResult<GoogleSignInResult> googleSignInResultOptionalPendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (AccessToken.getCurrentAccessToken() != null) {

            return SocialMediaDetector.facebook;
        } else if (Twitter.getSessionManager().getActiveSession() != null) {

            return SocialMediaDetector.twitter;
        } else if (googleSignInResultOptionalPendingResult.isDone()) {

            return SocialMediaDetector.google;
        }
        return SocialMediaDetector.nothing;
    }

    public static void addUsernameandEmailtosharedpref(Context context, String email, String name) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editorsharedpreference;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editorsharedpreference = sharedPreferences.edit();
        editorsharedpreference.putString(Constants.emailprefkey, email);
        editorsharedpreference.putString(Constants.nameprefkey, name);
        editorsharedpreference.commit();
    }

    public static void removeuserdatafromsharedpref(Context context) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editorsharedPrefrences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editorsharedPrefrences = sharedPreferences.edit();
        editorsharedPrefrences.remove(Constants.emailprefkey);
        editorsharedPrefrences.remove(Constants.nameprefkey);
        editorsharedPrefrences.commit();
    }

    public static String getStringFromSharedPref(String key, Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

}
