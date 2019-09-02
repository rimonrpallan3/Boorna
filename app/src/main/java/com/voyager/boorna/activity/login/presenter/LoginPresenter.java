package com.voyager.boorna.activity.login.presenter;

import android.util.Log;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.activity.login.view.ILoginView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by User on 23-Jan-19.
 */

public class LoginPresenter implements ILoginPresenter{

    ILoginView iLoginView;
    private static final String TAG = "LoginPresenter";
    UserDetails userDetails;



    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }



   /* @Override
    public void doLogin(String name, String passwd, String fireBaseToken) {
        this.name = name;
        this.passwd = passwd;
        this.fireBaseToken = fireBaseToken;
        UserDetails userDetails = new UserDetails();
        userDetails.setLoginType(normal);
        userDetails.setFcm(fireBaseToken);
        System.out.println("------- doLogin  email : "+ name +
                " Password : " + passwd);
        initUser();
        Boolean isLoginSuccess = true;
        final int code = userDetails.checkUserValidity(name,passwd);
        if (code!=0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        iLoginView.onLoginResult(result, code);
        validateLoginDataBaseApi(userDetails);
    }*/



    public void validateLoginDataBaseApi(final UserDetails userDetails){
      /*  Retrofit retrofit = new ApiClient().getRetrofitClient();
        final WebServices webServices = retrofit.create(WebServices.class);
        Observable<UserDetails> getLoginObservable;
        if(userDetails.getLoginType().equals(google)) {
            getLoginObservable = webServices.loginGoogleUser(userDetails.getEmail(), userDetails.getLoginType(), userDetails.getProfile_image(),userDetails.getUsermob(),userDetails.getUserName(),userDetails.getGoogleId(),userDetails.getFcm());
            loginType = userDetails.getLoginType() ;
        }else if(userDetails.getLoginType().equals(normal)){
            getLoginObservable = webServices.loginNormalUser(name, passwd, userDetails.getLoginType(),userDetails.getFcm());
            loginType = userDetails.getLoginType() ;
        }else if(userDetails.getLoginType().equals(facebook)){
            getLoginObservable = webServices.loginFBUser(userDetails.getEmail(), userDetails.getLoginType(), userDetails.getProfile_image(),userDetails.getUsermob(),userDetails.getUserName(),userDetails.getFcm());
            loginType = userDetails.getLoginType() ;
        }else {
            getLoginObservable = webServices.loginNormalUser(name, passwd, userDetails.getLoginType(),userDetails.getFcm());
            loginType = userDetails.getLoginType() ;
        }
        getLoginObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginDetails());
        System.out.println(" ------- validateLoginDataBaseApi ");*/




    }


    private Observer<UserDetails> getLoginDetails() {
        return new Observer<UserDetails>() {

            @Override
            public void onSubscribe(Disposable d) {
                iLoginView.unSubscribeCalls(d);
                Log.d("LoginPresenter", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(UserDetails value) {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                Log.d("LoginPresenter", " onComplete");
            }
        };
    }



    private void initUser(){
        userDetails = new UserDetails();
    }




    @Override
    public void doLogin(String emailPhno, String passwd, String fireBaseToken) {

    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {

    }

    @Override
    public void onLoginSucuess() {

    }
}
