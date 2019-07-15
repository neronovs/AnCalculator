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

                //Remove last zeros
                if (textFromRes.substring(textFromRes.length() - 1).equals("0")) {
                    for (int i = textFromRes.length() - 1; i >= textFromRes.indexOf("."); i--) {
                        if (textFromRes.substring(textFromRes.length() - 1).equals("0"))
                            textFromRes = textFromRes.substring(0, i);
                        else break;
                    }
                }
                //remove "." at the anding in a string
                if (textFromRes.charAt(textFromRes.length() - 1) == '.')
                    textFromRes = textFromRes.substring(0, textFromRes.indexOf("."));


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
                res = BigDecimal.valueOf(Double.parseDouble(mFirstNum))
                        .multiply(BigDecimal.valueOf(0.01d)).toString();
                break;
            case "SQRT":
                //Negative numbers cannot be SQRT
                if (mFirstNum.charAt(0) != '-')
                    res = String.valueOf(Math.sqrt(Double.parseDouble(mFirstNum)));
                else
                    res = "0";

                break;
            case "POW":
                BigDecimal bigDecRes;
                bigDecRes = BigDecimal.valueOf(Double.parseDouble(mFirstNum));
                res = String.valueOf(bigDecRes.pow(Integer.valueOf(mSecondNum)));
                break;
        }

        res = removeZerosAndDotsAtTheEnd(res);

        return makePropriateTextForNumberWithPoint(res);
    }

    //Removes "." and "0" at the end of a string
    private String removeZerosAndDotsAtTheEnd(String res) {
        if (res == null) res = "0";
        if (res.charAt(res.length() - 1) == '0') {
            for (int i = res.length() - 1; i >= res.indexOf("."); i--) {
                if (res.substring(i, i + 1).equals("0")) {
                    res = res.substring(0, i);
                } else if (res.substring(i, i + 1).equals(".")) {
                    res = res.substring(0, res.indexOf(".") + 1);
                    break;
                } else {
                    break;
                }
            }
        }

        return res;
    }

    private String makePropriateTextForNumberWithPoint(String text) {
        if (text.charAt(0) == '-') {
            if (text.length() > 2) {
                for (int i = 1; i < text.indexOf('.'); i++) {
                    if (text.charAt(1) == '0' && text.charAt(2) != '.')
                        text = "-" + text.substring(2);
                    else
                        break;
                }

                if (text.charAt(1) == '0' && text.length() > 2 && text.charAt(2) != '.')
                    text = "-" + text.substring(2);

            } else if (text.length() == 2) {
                text = "-" + text.charAt(1);
            } else {
                text = "-" + "0";
            }
        } else if (text.charAt(0) == '0') {
            boolean flagZeroFirst;
            for (int i = 0; i < text.indexOf("."); i++) {
                flagZeroFirst = text.charAt(i) == '0';

                if (!flagZeroFirst) {
                    text = text.substring(i);
                    break;
                }
            }
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
            Log.e(TAG, ": loadPrefs() error in sharedPreferences.getString(MEMORY_KEY, \"0\"). " + ex);
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