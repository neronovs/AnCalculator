package com.example.otc.ancalculator;

import android.content.SharedPreferences;

/**
 * Created by otc on 05.09.2017.
 */

public class Computer
        implements Model {

    private Computer model;
    public void setModel(Computer model) {
        if (this.model == null)
            this.model = (Computer) model;
    }

    private final String MEMORY_KEY = "memory_key";

    private SharedPreferences sharedPreferences;
//    public void setSharedPreferences(SharedPreferences sharedPreferences) {
//        this.sharedPreferences = sharedPreferences;
//    }

    private String tableInfo = "";
    private int action = 0; //1 - plus, 2 - minus, 3 - multiply, 4 - divide, 5 - dot
    private String first = "";
    private String second = "";

//    public static Computer getInstance() {
//        return computerInstance;
//    }
    public Computer(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void compute(String first, String second, int action, boolean equalPressed) {
        //No action no action
        if (model.action != 0 && !model.getSecond().equals("")) {

            double res = 0;
            String error = "";
            switch (action) {
                case 1:
                    res = Double.parseDouble(first) + Double.parseDouble(second);
                    break;
                case 2:
                    res = Double.parseDouble(first) - Double.parseDouble(second);
                    break;
                case 3:
                    res = Double.parseDouble(first) * Double.parseDouble(second);
                    break;
                case 4:
                    if (second.equals("0"))
                        error = "Divide by 0!";
                    else
                        res = Double.parseDouble(first) / Double.parseDouble(second);
                    break;
            }

            if (error.equals("")) {
                int resInteger = Integer.parseInt(String.valueOf(res).substring(0, String.valueOf(res).indexOf(".")));
                if (res - resInteger == 0.0)
                    model.tableInfo = String.valueOf(resInteger);
                else
                    model.tableInfo = String.valueOf(res);
            } else {
                model.tableInfo = error;
            }

            model.first = "";
            model.second = "";
            if (equalPressed)
                model.action = 0;
            else
                model.first = tableInfo;

            equalPressed = false;
        }
    }

    //region Memory block
    @Override
    public double memory(int action) {
        double mem = 0;
        //1 - add, 2 - remove, 3 - read, 4 - clean
        switch (action) {
            case 1:
                mem = loadPrefs() + Double.parseDouble(model.tableInfo);
                savePrefs(mem);
                break;
            case 2:
                mem = loadPrefs() - Double.parseDouble(model.tableInfo);
                savePrefs(mem);
                break;
            case 3:
                mem = loadPrefs();
                model.tableInfo = String.valueOf(mem);
                break;
            case 4:
                savePrefs(0);
                break;
        }
        return mem;
    }

    @Override
    public void clear() {
        model.first = "";
        model.second = "";
        model.action = 0;
        model.tableInfo = "";
    }

    private void savePrefs(double digit) {
        sharedPreferences
                .edit()
                .putFloat(MEMORY_KEY, (float) digit)
                .apply();
    }

    private double loadPrefs() {
        return sharedPreferences.getFloat(MEMORY_KEY, 0);
    }
    //endregion

    //region Working with table information
    @Override
    public void saveTableInfo(String tableInfo) {
        model.tableInfo = tableInfo;
    }

    @Override
    public String getTableInfo() {
        return model.tableInfo;
    }
    //endregion

    @Override
    public String getFirst() {
        return model.first;
    }

    //region Working with the first and the second numbers
    @Override
    public void setFirst(String first) {
        if (first.equals(".")) {
            if (!model.first.contains("."))
                model.first += first;
        } else {
            model.first += first;
        }

        model.tableInfo = model.first;
    }

    @Override
    public String getSecond() {
        return model.second;
    }

    @Override
    public void setSecond(String second) {
        if (second.equals(".")) {
            if (!model.second.contains("."))
                model.second += second;
        } else {
            model.second += second;
        }

        model.tableInfo = model.second;
    }
    //endregion

    //region Working with actions
    //1 - plus, 2 - minus, 3 - multiply, 4 - divide
    @Override
    public void setAction(int action) {
        if (!model.first.equals("") && !model.second.equals(""))
            compute(model.first, model.second, model.action, false);

        model.action = action;
    }
    @Override
    public int getAction() {
        return model.action;
    }
    //endregion
}