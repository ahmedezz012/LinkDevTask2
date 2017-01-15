package com.ezz.linkdevtask.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ezz.linkdevtask.R;
import com.ezz.linkdevtask.app.activities.Activity_News;
import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.helpers.GoogleServices;
import com.ezz.linkdevtask.helpers.Utilities;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class Fragment_Login extends Fragment {

    LoginButton btnOriginalfblogin;
    Button btnfacebooklogin, btntwiiterlogin, btngooglelogin;
    CallbackManager callbackManager;

    String email, username;

    TwitterLoginButton btnOriginaltwiiterlogin;

    GoogleSignInOptions googleSignInOptions;
    GoogleApiClient googleApiClient;

    SignInButton btnOriginalgooglelogin;
    int requestcode = 1000;


    public Fragment_Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleSignInOptions = GoogleServices.getgoogleSignInOptions();
        googleApiClient = GoogleServices.getgoogleApiClient(getActivity(), googleSignInOptions);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnOriginalfblogin = (LoginButton) view.findViewById(R.id.btnOriginalfblogin);
        btnfacebooklogin = (Button) view.findViewById(R.id.btnfacebooklogin);

        btntwiiterlogin = (Button) view.findViewById(R.id.btntwiiterlogin);
        btnOriginaltwiiterlogin = (TwitterLoginButton) view.findViewById(R.id.btnOriginaltwiiterlogin);

        btnOriginalgooglelogin = (SignInButton) view.findViewById(R.id.btnOriginalgooglelogin);
        btngooglelogin = (Button) view.findViewById(R.id.btngooglelogin);
        btngooglelogin.setOnClickListener(btngoogleloginclick);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //facebook
        btnOriginalfblogin.setReadPermissions(Arrays.asList(Constants.emailpermission));
        btnOriginalfblogin.setFragment(this);

        callbackManager = CallbackManager.Factory.create();

        btnOriginalfblogin.registerCallback(callbackManager, facebookCallback);
        btnfacebooklogin.setOnClickListener(btnfacebookloginclick);
        //twitter
        btntwiiterlogin.setOnClickListener(btntwiiterloginclick);
        btnOriginaltwiiterlogin.setCallback(twitterSessionCallback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        btnOriginaltwiiterlogin.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestcode) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result != null) {
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    username = account.getDisplayName();
                    email = account.getEmail();
                    Utilities.addUsernameandEmailtosharedpref(getActivity(), email, username);
                    gotoActivity_News();
                } else
                    displayErroToast(result.getStatus() + "");
            } else
                displayErroToast(getString(R.string.error));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }


    // facebook listeners
    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            if (loginResult != null) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), graphJSONObjectCallback);
                Bundle parameters = new Bundle();
                parameters.putString(Constants.fields, Constants.emailfield + "," + Constants.namefield);
                request.setParameters(parameters);
                request.executeAsync();
            } else {
                displayErroToast(getString(R.string.error));
            }
        }

        @Override
        public void onCancel() {
            displayErroToast(getString(R.string.canceled));
        }

        @Override
        public void onError(FacebookException error) {
            displayErroToast(getString(R.string.error));
        }
    };

    GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            if (object != null) {
                try {
                    username = object.getString(Constants.namefield);
                } catch (JSONException e) {
                    displayErroToast(e.getMessage());
                }
                try {
                    email = object.getString(Constants.emailfield);

                } catch (JSONException e) {
                    displayErroToast(e.getMessage());
                }
                Utilities.addUsernameandEmailtosharedpref(getActivity(), email, username);
                gotoActivity_News();
            } else
                displayErroToast(getString(R.string.error));
        }
    };

    View.OnClickListener btnfacebookloginclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnOriginalfblogin.performClick();
        }
    };
    //////
    //twitter listeneres
    Callback<TwitterSession> twitterSessionCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            if (result != null) {
                username = result.data.getUserName();
                Utilities.addUsernameandEmailtosharedpref(getActivity(), email, username);
                gotoActivity_News();
            } else
                displayErroToast(getString(R.string.error));
            //   TwitterAuthClient authClient = new TwitterAuthClient();
            // authClient.requestEmail(Twitter.getSessionManager().getActiveSession(),stringCallback);
        }

        @Override
        public void failure(TwitterException exception) {
            displayErroToast(getString(R.string.error));
        }
    };
    Callback<String> stringCallback = new Callback<String>() {
        @Override
        public void success(Result<String> result) {
            email = result.data;
        }

        @Override
        public void failure(TwitterException exception) {
        }
    };
    View.OnClickListener btntwiiterloginclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnOriginaltwiiterlogin.performClick();
        }
    };
    //////
    //google button listener
    View.OnClickListener btngoogleloginclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, requestcode);
        }
    };

    void gotoActivity_News() {
        Intent intent = new Intent(getActivity(), Activity_News.class);
        startActivity(intent);
        getActivity().finish();
    }

    void displayErroToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }
}
