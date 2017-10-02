package ru.narod.nod.ancalculator;

import android.content.SharedPreferences;

/**
 * Created by otc on 05.09.2017.
 */

public interface Model {

    //Put a url of an instance of model inside the model
    public void setModel(Computer model);

    //Calculation of the numbers
    String compute(boolean equalPressed);

    //Putting to or Getting from a digit memory
    String memory(int action); //1 - add, 2 - remove, 3 - read, 4 - clean

    //Clear fields
    void clear();

    //Working with table information
    void setTableInfo(String tableInfo);
    String getTableInfo();

    //Shared Preferences
    void setSharedPreferences(SharedPreferences sharedPreferences);

    //Calculate a percentage
    public String calculatePercentage(String stringNumber);

    //Working with the first and the second numbers
    void setFirst(String first);
    String getFirst();
    void setSecond(String second);
    String getSecond();
    //Working with an action
    void setAction(int act);
    int getAction();
    //To clear a field from 0 when it was pressed "-"
    void clearFirstField(boolean firstField);
    boolean isCurrentFieldFirst();
    void setCurrentFieldFirst(boolean currentFieldFirst);
    }
