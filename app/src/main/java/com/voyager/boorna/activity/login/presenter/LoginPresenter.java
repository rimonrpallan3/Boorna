package com.voyager.boorna.activity.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.voyager.boorna.activity.login.helper.Helper;
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
    SharedPreferences.Editor editor;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }



    @Override
    public void doLogin(String name, String passwd, String fireBaseToken, SharedPreferences.Editor editor) {
        this.name = name;
        this.passwd = passwd;
        this.fireBaseToken = fireBaseToken;
        this.editor = editor;
        initUser();
        UserDetails userDetails = new UserDetails();
        userDetails.setFcm(fireBaseToken);
        System.out.println("------- doLogin  email : "+ name +
                " Password : " + passwd+", fireBaseToken : "+fireBaseToken);
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

        if(userDetails.getFcm()==null){
            fireBaseToken = "1324532";
        }
        getLoginObservable = webServices.loginUser(name, passwd,fireBaseToken);

        getLoginObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLoginDetails());
        Log.d("LoginPresenter", " validateLoginDataBaseApi : ");


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
                userDetails= value;
                    /*System.out.println("-------validateLoginDataBaseApi  email : " + name +
                            " Password : " + passwd +
                            " LName : " + userDetails.getFirst_name()+
                            " phno : " + userDetails.getEmail() +
                            " city : " + userDetails.getEmail() +
                            "userID: " + userDetails.getId()+
                            "imageUrl"+ userDetails.getProfile_src());*/
                Gson gson = new Gson();
                String jsonString = gson.toJson(userDetails);

                System.out.println(" -----------LoginPresenter getLoginDetails  validateLoginDataBaseApi "+jsonString);
                if(jsonString!=null) {
                    System.out.println("-----------getLoginDetails onNext "+jsonString);
                }
                    /*if(userDetails.getError_status()==null){
                        userDetails.setError_status("");
                    }*/

                final int code = userDetails.validateLoginResponseError(userDetails.getLogin_status() );
                System.out.println("--------- validateLoginDataBaseApi code: "+code);
                Boolean isLoginSuccess = true;
                if (code != 0) {
                    isLoginSuccess = false;
                    //System.out.println("--------- validateLoginDataBaseApi isError: "+userDetails.getLogin_status() +" Error message: "+userDetails.getLogin_status());
                   // Toast.makeText((Context) iLoginView, userDetails.getLogin_status(), Toast.LENGTH_SHORT).show();
                    Log.d("LoginPresenter", "validateLoginDataBaseApi  data unSuccess : "+userDetails.getMessage());
                    iLoginView.showErrorMsg(userDetails.getMessage());
                } else {
                    userDetails.setPassword(passwd);
                    userDetails.setFcm(fireBaseToken);
                    Helper.addUserGsonInSharedPrefrences(userDetails,editor);
                    Log.d("LoginPresenter", "validateLoginDataBaseApi  data Successful : "+userDetails.getMessage());
                }
                Boolean result = isLoginSuccess;
                //System.out.println("----- sendRegisteredDataAndValidateResponse second Data Please see, code = " + code + ", result: " + result);
                iLoginView.onLoginResponse(result, code);
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
        iLoginView.sendPParcelableObj(userDetails);
    }
}
