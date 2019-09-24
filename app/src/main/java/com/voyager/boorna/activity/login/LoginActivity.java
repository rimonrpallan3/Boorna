package com.voyager.boorna.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.landing.LandingActivity;
import com.voyager.boorna.activity.landing.receiver.LocationUpdatesBroadcastReceiver;
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
    private static final String TAG = LoginActivity.class.getSimpleName();

    Button btnSignIn;
    AppCompatEditText etEmail;
    AppCompatEditText etCPass;
    AppCompatTextView tvForgotPswd;
    AppCompatTextView tvErrorMsg;

    ILoginPresenter iLoginPresenter;

    FrameLayout loadingLayout;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    Bundle bundle;
    String language = "";
    Disposable dMainListObservable;
    String fireBaseToken="";
    LinearLayout llErrorMsg;
    String value="";
    UserDetails userDetails;
    private int SPLASH_DISPLAY_LENGTH = 2;
    private FirebaseAuth mAuth;
    FirebaseUser currentUserFireBase;


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
        value =getUserGsonInSharedPrefrences();
        if (value != null && value.length() > 0) {
            intent = new Intent(getApplicationContext(), LandingActivity.class);
            intent.putExtra("UserDetails", userDetails);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "No value present  : ");
        }



        loadingLayout = findViewById(R.id.loadingLayout);
        iLoginPresenter = new LoginPresenter(this);
        fireBaseToken = FirebaseInstanceId.getInstance().getToken();
        mAuth = FirebaseAuth.getInstance();
        Log.d("SplashPresenter", "fireBaseToken  : " +fireBaseToken);
        FirebaseUser currentUserFireBase = mAuth.getCurrentUser();


    }




    public void setLoader(int visibility){
        loadingLayout.setVisibility(visibility);
    }

    /*@Override
    public void onSetProgressBarVisibility(int visibility) {
        loadingLayout.setVisibility(visibility);
    }*/


    public void btnSignIn(View view){
        /*Intent intent = new Intent(this, LandingActivity.class);

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

    public String getUserGsonInSharedPrefrences(){
        String emailAddress ="";
        Gson gson = new Gson();
        String json = sharedPrefs.getString("UserDetails", null);
        if(json!=null){
             userDetails = gson.fromJson(json, UserDetails.class);
            if(userDetails.getEmail()!=null) {
                emailAddress = userDetails.getUser_email();
            }else {
                emailAddress = "";
            }
            System.out.println(TAG+"---------  getUserGsonInSharedPrefrences"+json);
        }
        return emailAddress;
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
            System.out.println(TAG+"---------  onLoginResult");
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
            System.out.println(TAG+"---------  onLoginResponse");
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
        System.out.println(TAG+"---------  sendPParcelableObj");
        Intent intent = new Intent(this, LandingActivity.class);
        intent.putExtra("LoginDone", "Done");
        Gson gson = new Gson();
        String jsonString = gson.toJson(userDetails);

        System.out.println(TAG+" -----------  sendPParcelableObj "+jsonString);
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