package com.example.otc.ancalculator;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by otc on 05.09.2017.
 */

public interface MainPresenter
        extends MvpPresenter<MainView> {

    //Treatment of a button pressing
    void buttonPressed(int btn);

    //Showing a result of action
    void showResult(String text);
}
