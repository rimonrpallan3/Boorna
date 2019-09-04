package com.voyager.boorna.activity.landing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.voyager.boorna.R;
import com.voyager.boorna.activity.login.presenter.ILoginPresenter;
import com.voyager.boorna.activity.login.presenter.LoginPresenter;
import com.voyager.boorna.activity.login.view.ILoginView;
import com.voyager.boorna.appconfig.Helper;

import io.reactivex.disposables.Disposable;

public class LandingActivity extends AppCompatActivity   {



    private static final String TAG = "LandingActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }
}