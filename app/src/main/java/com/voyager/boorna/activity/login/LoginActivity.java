package com.voyager.boorna.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.login.presenter.ILoginPresenter;
import com.voyager.boorna.activity.login.view.ILoginView;
import com.voyager.boorna.appconfig.Helper;


import io.reactivex.disposables.Disposable;

/**
 * Created by User on 23-Jan-19.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {


    Button btnSignInGoogle;
    Button btnSignInFB;
    Button btnSignIn;
    AppCompatEditText etEmail;
    AppCompatEditText etCPass;
    private static final String TAG = "SignInPage";
    ILoginPresenter iLoginPresenter;

    FrameLayout loadingLayout;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    Bundle bundle;
    String language = "";
    Disposable dMainListObservable;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        bundle = new Bundle();

        etEmail =  findViewById(R.id.etEmail);
        etCPass =  findViewById(R.id.etCPass);


        sharedPrefs = getSharedPreferences(Helper.UserDetails, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        loadingLayout = (FrameLayout) findViewById(R.id.loadingLayout);


    }

    public void setLoader(int visibility){
        loadingLayout.setVisibility(visibility);
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }

   /* @Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }*/



   /* @Override
    public void sendPParcelableObj(UserDetails userDetails) {
        Intent intent = new Intent(this, LandingPage.class);
        intent.putExtra("LoginDone", "Done");
        intent.putExtra("language", language);
        setResult(Helper.REQUEST_LOGEDIN, intent);
        intent.putExtra("UserDetails", userDetails);
        startActivity(intent);
        finish();
    }
*/




    @Override
    public void unSubscribeCalls(Disposable dMainListObservable) {
        this.dMainListObservable =dMainListObservable;
    }



    @Override
    public void onStart() {
        super.onStart();
        /*if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }*/
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dMainListObservable!=null)
            dMainListObservable.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}