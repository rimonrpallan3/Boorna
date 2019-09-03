package com.voyager.boorna.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.login.presenter.ILoginPresenter;
import com.voyager.boorna.activity.login.presenter.LoginPresenter;
import com.voyager.boorna.activity.login.view.ILoginView;
import com.voyager.boorna.appconfig.Helper;
import com.voyager.boorna.appconfig.NetworkDetector;


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
    String fireBaseToken="";


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
        iLoginPresenter = new LoginPresenter(this);
        fireBaseToken = FirebaseInstanceId.getInstance().getToken();

    }

    public void setLoader(int visibility){
        loadingLayout.setVisibility(visibility);
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }


    public void btnSignIn(View view){
        Helper.hideKeyboard(this);
        if(NetworkDetector.haveNetworkConnection(this)){
            //Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network_available), Snackbar.LENGTH_SHORT).show();
            iLoginPresenter.setProgressBarVisiblity(View.VISIBLE);
            btnSignIn.setEnabled(false);
            iLoginPresenter.doLogin(etEmail.getText().toString(), etCPass.getText().toString(),fireBaseToken);
        }else {
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network), Snackbar.LENGTH_LONG).show();

        }
        iLoginPresenter.doLogin(etEmail.getText().toString(),etCPass.getText().toString(),fireBaseToken);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        iLoginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnSignInGoogle.setEnabled(true);
        btnSignInFB.setEnabled(true);
        btnSignIn.setEnabled(true);
        if (result){
        }
        else {
            //Toast.makeText(this, "Please input Values, code = " + code, Toast.LENGTH_SHORT).show();
            btnSignInGoogle.setEnabled(true);
            btnSignInFB.setEnabled(true);
            btnSignIn.setEnabled(true);
        }
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