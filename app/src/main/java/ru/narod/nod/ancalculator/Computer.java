package ru.narod.nod.ancalculator;

import android.content.SharedPreferences;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.util.Log;


class Computer implements ru.narod.nod.ancalculator.Model {

    private final String TAG = getClass().getName();
    private Computer model;

    public void setModel(Computer model) {
        if (this.model == null)
            this.model = model;
    }

    private final String MEMORY_KEY = "calculator_shared";

    private SharedPreferences sharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private String tableInfo = "0";
    private int action = 0; //1 - plus, 2 - minus, 3 - multiply, 4 - divide, 5 - dot
    private String first = "";
    private String second = "";

    private boolean isCurrentFieldFirst = true;

    public boolean isCurrentFieldFirst() {
        return isCurrentFieldFirst;
    }

    public void setCurrentFieldFirst(boolean currentFieldFirst) {
        isCurrentFieldFirst = currentFieldFirst;
    }

    Computer(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public String compute(boolean equalPressed) {
        BigDecimal res = BigDecimal.ZERO;

        isCurrentFieldFirst = true; //now there will be called the fist number again

        //No action no action
        if (model.action != 0 && !model.getSecond().equals("")) {

            String error = "";
            switch (model.action) {
                case 1:
                    res = BigDecimal.valueOf(Double.parseDouble(model.first)).add(
                            BigDecimal.valueOf(Double.parseDouble(model.second)));
                    break;
                case 2:
                    res = BigDecimal.valueOf(Double.parseDouble(model.first)).subtract(
                            BigDecimal.valueOf(Double.parseDouble(model.second)));
                    break;
                case 3:
                    res = BigDecimal.valueOf(Double.parseDouble(model.first)).multiply(
                            BigDecimal.valueOf(Double.parseDouble(model.second)));
                    break;
                case 4:
                    if (second.equals("0"))
                        error = "Divide by 0!";
                    else
                        res = BigDecimal.valueOf(Double.parseDouble(model.first)).divide(
                                BigDecimal.valueOf(Double.parseDouble(model.second))
                                , 30, RoundingMode.CEILING);
                    break;
            }

            if (error.equals("")) {
                //Make text from BigDecimal
                String textFromRes = makePropriateTextForNumberWithPoint(String.valueOf(res));

                if (textFromRes.contains(".") &&
                        textFromRes.substring(textFromRes.indexOf(".") + 1).equals("0"))
                    model.tableInfo = textFromRes.substring(0, textFromRes.indexOf("."));
                else
                    model.tableInfo = textFromRes;
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
        return model.tableInfo;
    }

    @Override
    public String calculateWithParametres(String mFirstNum, final String mSecondNum, String mAct) {
        String res = null;
        switch (mAct) {
            case "%":
                res = makePropriateTextForNumberWithPoint(
                        BigDecimal.valueOf(Double.parseDouble(mFirstNum))
                                .multiply(BigDecimal.valueOf(0.01d)).toString());
                break;
            case "SQRT":
                double tempDouble = 0d;
                tempDouble = Math.sqrt(Double.parseDouble(mFirstNum));
                res = makePropriateTextForNumberWithPoint(
                        String.valueOf(
                                tempDouble));
                break;
            case "POW":
                BigDecimal bigDecRes;// = BigDecimal.ZERO;
                bigDecRes = BigDecimal.valueOf(Double.parseDouble(mFirstNum));
                res = makePropriateTextForNumberWithPoint(
                        String.valueOf(
                                bigDecRes.pow(Integer.valueOf(mSecondNum))));
                break;
        }

        return res;
    }

    private String makePropriateTextForNumberWithPoint(String text) {
        if (text.contains(".")) {
            for (int i = text.length() - 1; i >= text.indexOf("."); i--) {
                if (text.substring(i, i + 1).equals("0")) {
                    text = text.substring(0, i);
                }
//                else if (text.substring(i, i + 1).equals(".")) {
//                    text = text.substring(0, text.indexOf("."));
//                    break;
//                } else {
//                    break;
//                }
            }
        }

        if (text.charAt(0) == '-')
            if (text.length() > 2) {
                if (text.charAt(1) == '0' && text.charAt(2) != '.')
                    text = "-" + text.substring(2);
            } else {
                text = "-" + "0";
            }

        return text;
    }

    //region Memory block
    @Override
    public String memory(int memoryAction) {
        String mem = "";
        //1 - add, 2 - remove, 3 - read, 4 - clean
        switch (memoryAction) {
            case 1:
                try {
                    mem = makePropriateTextForNumberWithPoint(String.valueOf(
                            BigDecimal.valueOf(Double.parseDouble(loadPrefs())).add(
                                    BigDecimal.valueOf(Double.parseDouble(model.tableInfo)))));
                } catch (NumberFormatException ex) {
                    Log.e(TAG, ": memory() error in adding in memory. " + ex);
                    mem = "0";
                }
                savePrefs(mem);
                break;
            case 2:
                try {
                    mem = makePropriateTextForNumberWithPoint(String.valueOf(
                            BigDecimal.valueOf(Double.parseDouble(loadPrefs())).subtract(
                                    BigDecimal.valueOf(Double.parseDouble(model.tableInfo)))));
                    savePrefs(mem);
                } catch (NumberFormatException ex) {
                    Log.e(TAG, ": memory() error in removing in memory. " + ex);
                }
                break;
            case 3:
                mem = makePropriateTextForNumberWithPoint(loadPrefs());
                break;
            case 4:
                savePrefs("0");
                break;
        }
        return mem;
    }

    @Override
    public void clear() {
        model.first = "";
        model.second = "";
        model.action = 0;
        model.tableInfo = "0";
        isCurrentFieldFirst = true;
    }

    private void savePrefs(String digit) {
        model.sharedPreferences
                .edit()
                .putString(MEMORY_KEY, digit)
                .apply();
    }

    private String loadPrefs() {
        try {
            return model.sharedPreferences.getString(MEMORY_KEY, "0");
        } catch (Exception ex) {
//            Log.e(TAG, ": loadPrefs() error in sharedPreferences.getString(MEMORY_KEY, \"0\"). " + ex);
        }

        return "0";
    }
    //endregion

    //region Working with table information
    @Override
    public void setTableInfo(String tableInfo) {
        model.tableInfo = tableInfo;
    }

    @Override
    public String getTableInfo() {
        return model.tableInfo;
    }
    //endregion

    //region Working with the first and the second numbers
    @Override
    public String getFirst() {
        return model.first;
    }

    @Override
    public void setFirst(String first) {
        if (first.equals(".")) {
            if (!model.first.contains("."))
                model.first += first;
        } else {
            model.first += first;
        }

        model.tableInfo = model.first = makePropriateTextForNumberWithPoint(model.first);
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

        model.tableInfo = model.second = makePropriateTextForNumberWithPoint(model.second);
    }
    //endregion

    //region Working with actions
    //1 - plus, 2 - minus, 3 - multiply, 4 - divide
    @Override
    public void setAction(int action) {
        isCurrentFieldFirst = false;

        if (!model.first.equals("") && !model.second.equals(""))
            compute(false);

        model.action = action;
    }

    @Override
    public int getAction() {
        return model.action;
    }
    //endregion

    //clear field
    public void clearFirstField(boolean firstField) {
        if (firstField)
            model.first = "";
        else
            model.second = "";
    }
}