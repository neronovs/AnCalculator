package ru.narod.nod.ancalculator;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

/**
 * Created by otc on 05.09.2017.
 */

public interface MainPresenter
        extends MvpPresenter<ru.narod.nod.ancalculator.MainView> {

    //Treatment of a button pressing
    void buttonPressed(int btn);

    //Showing a result of action
    void showResult(String text);

    //Work with the link to a model instance from itself
    ru.narod.nod.ancalculator.Model getModel();
    void setModel(ru.narod.nod.ancalculator.Model model);
}
