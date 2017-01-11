package com.ezz.linkdevtask.helpers;

import android.content.Context;

import com.ezz.linkdevtask.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Ahmed.Ezz on 1/11/2017.
 */

public class GoogleServices {
    public static GoogleSignInOptions getgoogleSignInOptions()
    {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }
    public static GoogleApiClient getgoogleApiClient(Context context,GoogleSignInOptions googleSignInOptions)
    {
        return  new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

}
