package com.voyager.boorna.activity.login.view;



import io.reactivex.disposables.Disposable;

/**
 * Created by User on 23-Jan-19.
 */

public interface ILoginView {
    void setLoader(int visibility);
    void onSetProgressBarVisibility(int visibility);
    void unSubscribeCalls(Disposable dMainListObservable);
}
