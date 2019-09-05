package com.voyager.boorna.activity.login.view;



import com.voyager.boorna.activity.login.model.UserDetails;

import io.reactivex.disposables.Disposable;

/**
 * Created by User on 23-Jan-19.
 */

public interface ILoginView {
    void unSubscribeCalls(Disposable dMainListObservable);
    void onLoginResult(Boolean result, int code);
    void onLoginResponse(Boolean result, int code);
    void sendPParcelableObj(UserDetails userDetails);
    void showErrorMsg(String errorMsg);
}
