package com.voyager.boorna.activity.landing.presenter;

import com.voyager.boorna.activity.landing.view.ILandingView;

public class LandingPresenter implements ILandingPresenter {

    ILandingView iLandingView;


    public LandingPresenter(ILandingView iLandingView) {
        this.iLandingView = iLandingView;
    }
}
