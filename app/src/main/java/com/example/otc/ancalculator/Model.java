package com.example.otc.ancalculator;

/**
 * Created by otc on 05.09.2017.
 */

public interface Model {

    //Put a url of an instance of model inside the model
    public void setModel(Computer model);

    //Calculation of the numbers
    void compute(String first, String second, int action, boolean equalPressed);

    //Putting to or Getting from a digit memory
    double memory(int action); //1 - add, 2 - remove, 3 - read, 4 - clean

    //Clear fields
    void clear();

    //Working with table information
    void saveTableInfo(String tableInfo);
    String getTableInfo();

    //Working with the first and the second numbers
    void setFirst(String first);
    String getFirst();
    void setSecond(String second);
    String getSecond();
    //Working with an action
    void setAction(int act);
    int getAction();
}
