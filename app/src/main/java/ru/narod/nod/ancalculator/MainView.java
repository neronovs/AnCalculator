package ru.narod.nod.ancalculator;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by otc on 05.09.2017.
 */

public interface MainView
        extends MvpView {

    //Show an got info on the table
    void showTable(String view, String text);

}
