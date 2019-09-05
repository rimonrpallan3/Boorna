package com.voyager.boorna.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.landing.LandingActivity;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.activity.login.presenter.ILoginPresenter;
import com.voyager.boorna.activity.login.presenter.LoginPresenter;
import com.voyager.boorna.activity.login.view.ILoginView;
import com.voyager.boorna.activity.resetPass.PswdResetActivity;
import com.voyager.boorna.appconfig.Helper;
import com.voyager.boorna.appconfig.NetworkDetector;


import androidx.appcompat.widget.AppCompatTextView;
import io.reactivex.disposables.Disposable;

/**
 * Created by User on 23-Jan-19.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {


    Button btnSignIn;
    AppCompatEditText etEmail;
    AppCompatEditText etCPass;
    AppCompatTextView tvForgotPswd;
    AppCompatTextView tvErrorMsg;
    private static final String TAG = "SignInPage";
    ILoginPresenter iLoginPresenter;

    FrameLayout loadingLayout;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    Bundle bundle;
    String language = "";
    Disposable dMainListObservable;
    String fireBaseToken="";
    LinearLayout llErrorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        bundle = new Bundle();

        etEmail =  findViewById(R.id.etEmail);
        etCPass =  findViewById(R.id.etCPass);
        btnSignIn =  findViewById(R.id.btnSignIn);
        tvForgotPswd =  findViewById(R.id.tvForgotPswd);
        llErrorMsg =  findViewById(R.id.llErrorMsg);
        tvErrorMsg =  findViewById(R.id.tvErrorMsg);

        sharedPrefs = getSharedPreferences(Helper.UserDetails, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        loadingLayout = findViewById(R.id.loadingLayout);
        iLoginPresenter = new LoginPresenter(this);
        //fireBaseToken = FirebaseInstanceId.getInstance().getToken();

    }

    public void setLoader(int visibility){
        loadingLayout.setVisibility(visibility);
    }

    /*@Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }*/


    public void btnSignIn(View view){
  /*      Intent intent = new Intent(this, LandingActivity.class);

        startActivity(intent);*/
        //finish();
        Helper.hideKeyboard(this);
        if(NetworkDetector.haveNetworkConnection(this)){
            llErrorMsg.setVisibility(View.GONE);
            //Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network_available), Snackbar.LENGTH_SHORT).show();
            iLoginPresenter.setProgressBarVisiblity(View.VISIBLE);
            btnSignIn.setEnabled(false);
            iLoginPresenter.doLogin(etEmail.getText().toString(), etCPass.getText().toString(),fireBaseToken,editor);
        }else {
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network), Snackbar.LENGTH_LONG).show();

        }
    }

    public void tvForgotPswd(View view){
        Intent intent = new Intent(this, PswdResetActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        iLoginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnSignIn.setEnabled(true);
        if (result){
        }
        else {
            //Toast.makeText(this, "Please input Values, code = " + code, Toast.LENGTH_SHORT).show();
            btnSignIn.setEnabled(true);
        }
    }

    @Override
    public void onLoginResponse(Boolean result, int code) {
        iLoginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnSignIn.setEnabled(true);
        if (result){
            iLoginPresenter.onLoginSucuess();
        }
        else {
            //Toast.makeText(this,  getResources().getString(R.string.login_error_txt), Toast.LENGTH_SHORT).show();
            btnSignIn.setEnabled(true);

        }
    }

   /* @Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }*/



    @Override
    public void sendPParcelableObj(UserDetails userDetails) {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.putExtra("LoginDone", "Done");
        Gson gson = new Gson();
        String jsonString = gson.toJson(userDetails);

        System.out.println(" ----------- LoginPresenter sendPParcelableObj "+jsonString);
        setResult(Helper.REQUEST_LOGEDIN, intent);
        intent.putExtra("UserDetails", userDetails);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        llErrorMsg.setVisibility(View.VISIBLE);
        tvErrorMsg.setText(errorMsg);
    }


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