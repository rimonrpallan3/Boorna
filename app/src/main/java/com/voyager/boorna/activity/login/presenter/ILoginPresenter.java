package com.voyager.boorna.activity.login.presenter;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by User on 23-Jan-19.
 */

public interface ILoginPresenter {
    void doLogin(String emailPhno, String passwd, String fireBaseToken);
    void setProgressBarVisiblity(int visiblity);
    void onLoginSucuess();
}
