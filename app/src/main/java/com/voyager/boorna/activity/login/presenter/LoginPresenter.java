package com.voyager.boorna.activity.login.presenter;

import android.util.Log;
import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.activity.login.view.ILoginView;
import com.voyager.boorna.webservices.ApiClient;
import com.voyager.boorna.webservices.WebServices;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by User on 23-Jan-19.
 */

public class LoginPresenter implements ILoginPresenter{

    ILoginView iLoginView;
    private static final String TAG = "LoginPresenter";
    UserDetails userDetails;
    String name;
    String passwd;
    String fireBaseToken="";


    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }



    @Override
    public void doLogin(String name, String passwd, String fireBaseToken) {
        this.name = name;
        this.passwd = passwd;
        this.fireBaseToken = fireBaseToken;
        UserDetails userDetails = new UserDetails();
        userDetails.setFcmToken(fireBaseToken);
        System.out.println("------- doLogin  email : "+ name +
                " Password : " + passwd);
        initUser();
        Boolean isLoginSuccess = true;
        final int code = userDetails.checkUserValidity(name,passwd);
        if (code!=0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        iLoginView.onLoginResult(result, code);
        validateLoginDataBaseApi(userDetails);
    }



    public void validateLoginDataBaseApi(final UserDetails userDetails){
        Retrofit retrofit = new ApiClient().getRetrofitClient();
        final WebServices webServices = retrofit.create(WebServices.class);
        Observable<UserDetails> getLoginObservable;
        getLoginObservable = webServices.loginUser(name, passwd,"1324532");

        getLoginObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginDetails());
        System.out.println(" ------- validateLoginDataBaseApi ");

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
    public void setProgressBarVisiblity(int visiblity) {

    }

    @Override
    public void onLoginSucuess() {

    }
}
