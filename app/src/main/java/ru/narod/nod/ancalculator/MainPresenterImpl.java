package ru.narod.nod.ancalculator;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


class MainPresenterImpl
        extends MvpBasePresenter<ru.narod.nod.ancalculator.MainView>
        implements MainPresenter {

    private final String SHARED_NAME = "calculator_shared";

    private final String MAINVIEW_TABLE = "mainView_table";
    private final String MAINVIEW_MEMO = "mainView_memo";
    private final String MAINVIEW_ACTION = "mainView_action";
    private final String MAINVIEW_FIRSTNUMBER = "mainView_firstNumber";
    private final String MAINVIEW_SECONDNUMBER = "mainView_secondNumber";

    private ru.narod.nod.ancalculator.Model model;

    public ru.narod.nod.ancalculator.Model getModel() {
        return this.model;
    }

    public void setModel(ru.narod.nod.ancalculator.Model model) {
        this.model = model;
    }

    MainPresenterImpl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        if (model == null) {
            model = new Computer(sharedPreferences);
            model.setModel((Computer) model);
        }
    }

    @Override
    public void buttonPressed(int btn) {
        if (btn == R.id.buttonMPlus) {
            model.memory(1);
        } else if (btn == R.id.buttonMMinus) {
            model.memory(2);
        } else if (btn == R.id.buttonMR) {
            if (model.getAction() == 0) {
                model.clearFirstField(true);
                model.setFirst(model.memory(3));
            } else {
                model.clearFirstField(false);
                model.setSecond(model.memory(3));
            }
        } else if (btn == R.id.buttonMC) {
            model.memory(4);
        } else if (btn == R.id.button0) {
            buttonNumberTreatment(0);
        } else if (btn == R.id.button1) {
            buttonNumberTreatment(1);
        } else if (btn == R.id.button2) {
            buttonNumberTreatment(2);
        } else if (btn == R.id.button3) {
            buttonNumberTreatment(3);
        } else if (btn == R.id.button4) {
            buttonNumberTreatment(4);
        } else if (btn == R.id.button5) {
            buttonNumberTreatment(5);
        } else if (btn == R.id.button6) {
            buttonNumberTreatment(6);
        } else if (btn == R.id.button7) {
            buttonNumberTreatment(7);
        } else if (btn == R.id.button8) {
            buttonNumberTreatment(8);
        } else if (btn == R.id.button9) {
            buttonNumberTreatment(9);
        } else if (btn == R.id.buttonDot) {
            buttonActionTreatment(101);
        } else if (btn == R.id.buttonPlus) {
            buttonActionTreatment(1);
        } else if (btn == R.id.buttonMinus) {
            buttonActionTreatment(2);
        } else if (btn == R.id.buttonMultiply) {
            buttonActionTreatment(3);
        } else if (btn == R.id.buttonDivide) {
            buttonActionTreatment(4);
        } else if (btn == R.id.buttonEqual) {
            buttonActionTreatment(5);
        } else if (btn == R.id.buttonBack) {
            buttonActionTreatment(6);
        } else if (btn == R.id.buttonReverse) {
            buttonActionTreatment(7);
        } else if (btn == R.id.buttonPercent) {
            buttonActionTreatment(8);
        } else if (btn == R.id.buttonSQRT) {
            buttonActionTreatment(9);
        } else if (btn == R.id.buttonPow) {
            buttonActionTreatment(10);
        } else if (btn == R.id.buttonC) {
            model.clear();
        }

        showResult(model.getTableInfo());
    }

    private void buttonNumberTreatment(int num) {
        //In case that action was not set
        if (model.getAction() < 1 || model.getAction() > 4) {
            model.setCurrentFieldFirst(true);
            if (num == 101)
                //If after "-" is going "." than put "0" after "-"
                if (model.getFirst().equals("-")) {
                    model.setFirst("0.");
                } else {
                    model.setFirst(".");
                }
            else if (num == 0 || model.getFirst().equals("0")) {
                //Checking fot NotDoubleZero in the beginning of a string
                if (!model.getFirst().equals("0")) {
                    model.setFirst(String.valueOf(num));
                } else if (model.getFirst().length() > 1) {
                    model.setFirst(String.valueOf(num));
                } else {
                    model.clearFirstField(true);
                    model.setFirst(String.valueOf(num));
                }
            } else {
                model.setFirst(String.valueOf(num));
            }
            //Action was set
        } else {
            model.setCurrentFieldFirst(false);
            if (num == 101)
                if (model.getSecond().equals(""))
                    model.setSecond("0.");
                else
                    model.setSecond(".");
            else if (num == 0 || model.getSecond().equals("0")) {
                //Checking fot NotDoubleZero in the beginning of a string
                if (!model.getSecond().equals("0")) {
                    model.setSecond(String.valueOf(num));
                } else if (model.getSecond().length() > 1) {
                    model.setSecond(String.valueOf(num));
                } else {
                    model.clearFirstField(false);
                    model.setSecond(String.valueOf(num));
                }
            } else
                model.setSecond(String.valueOf(num));
        }
    }

    private void buttonActionTreatment(int num) {
        //If we have a result and press an action btn than the result will be a FirstNumber
        if (!model.getTableInfo().equals("") && model.getFirst().equals("")) {
            model.setFirst(model.getTableInfo());
        }

        switch (num) {
            case 1 -> //plus
                    model.setAction(1);
            case 2 -> { //minus
                //region Minus
                if (model.isCurrentFieldFirst()) {
                    if (model.getFirst().equals("0")) {
                        model.clearFirstField(model.isCurrentFieldFirst());
                        model.setFirst("-");
                    } else
                        model.setAction(2);
                } else {
                    if (model.getSecond().equals("0")) {
                        model.clearFirstField(model.isCurrentFieldFirst());
                        model.setSecond("-");
                    } else
                        model.setAction(2);
                }
            }
            //endregion
            case 3 -> //multiply
                    model.setAction(3);
            case 4 -> //divide
                    model.setAction(4);
            case 5 -> //equal
                    model.compute(true);
            case 6 -> { //buttonBack
                //region Backword function
                if (model.isCurrentFieldFirst()) {
                    if (model.getFirst().equals("") && model.getAction() != 0)
                        model.setFirst(model.getTableInfo()); //if the first number is empty, makes the first number with the table info
                    if (model.getFirst().length() > 1) {
                        String tempStr = model.getFirst().substring(0, model.getFirst().length() - 1);
                        model.clearFirstField(true);
                        model.setFirst(tempStr);
                    } else {
                        model.clearFirstField(true);
                        model.setFirst("0");
                    }
                } else {
                    if (model.getSecond().equals("") && model.getAction() != 0)
                        model.setSecond(model.getTableInfo()); //if the second number is empty, makes the second number with the table info
                    if (model.getSecond().length() > 1) {
                        String tempStr = model.getSecond().substring(0, model.getSecond().length() - 1);
                        model.clearFirstField(false);
                        model.setSecond(tempStr);
                    } else {
                        model.clearFirstField(false);
                        model.setSecond("0");
                    }
                }
            }
            //endregion
            case 7 -> { //buttonReverse
                //region ReveØrse function
                if (model.isCurrentFieldFirst()) {
                    String tempStr = model.getFirst();
                    model.clearFirstField(true);
                    if (tempStr.charAt(0) == '-')
                        model.setFirst(tempStr.substring(1)); //change polarity to positive
                    else {
                        model.setFirst("-" + tempStr); //change polarity to negative
                    } //cannot be empty string
                } else {
                    String tempStr = model.getSecond();
                    model.clearFirstField(false);
                    if (tempStr.length() > 0) {
                        if (tempStr.charAt(0) == '-')
                            model.setSecond(tempStr.substring(1)); //change polarity to positive
                        else {
                            model.setSecond("-" + tempStr); //change polarity to negative
                        }
                    } else {
                        model.setSecond("-"); //no symbols in 2nd string at all
                    }
                }
            }
            //endregion
            case 8 -> { //buttonPercent
                //region Percentage function
                if (model.isCurrentFieldFirst()) {
                    String tempStr = model.getFirst();
                    model.clearFirstField(true);
                    model.setFirst(model.calculateWithParametres(tempStr, null, "%"));
                } else {
                    String tempStr = model.getSecond();
                    model.clearFirstField(false);
                    if (tempStr.length() > 0)
                        model.setSecond(model.calculateWithParametres(tempStr, null, "%"));
                }
            }
            //endregion
            case 9 -> { //buttonSQRT
                //region Square root function
                if (model.isCurrentFieldFirst()) {
                    String tempStr = model.getFirst();
                    model.clearFirstField(true);
                    model.setFirst(model.calculateWithParametres(tempStr, "2", "SQRT"));
                } else {
                    String tempStr = model.getSecond();
                    model.clearFirstField(false);
                    if (tempStr.length() > 0)
                        model.setSecond(model.calculateWithParametres(tempStr, "2", "SQRT"));
                }
            }
            //endregion
            case 10 -> { //buttonPow
                //region Power function
                if (model.isCurrentFieldFirst()) {
                    String tempStr = model.getFirst();
                    model.clearFirstField(true);
                    model.setFirst(model.calculateWithParametres(tempStr, "2", "POW"));
                } else {
                    String tempStr = model.getSecond();
                    model.clearFirstField(false);
                    if (tempStr.length() > 0)
                        model.setSecond(model.calculateWithParametres(tempStr, "2", "POW"));
                }
            }
            //endregion
            case 101 -> //dot
                    buttonNumberTreatment(101);
        }
        showResult(model.getTableInfo());
    }

    @Override
    public void showResult(String text) {
        ifViewAttached(mainView -> {
            mainView.showTable(MAINVIEW_TABLE, text);

            mainView.showTable(MAINVIEW_FIRSTNUMBER, model.getFirst());

            //1 - plus, 2 - minus, 3 - multiply, 4 - divide
            switch (model.getAction()) {
                case 0 -> mainView.showTable(MAINVIEW_ACTION, "");
                case 1 -> mainView.showTable(MAINVIEW_ACTION, " + ");
                case 2 -> mainView.showTable(MAINVIEW_ACTION, " - ");
                case 3 -> mainView.showTable(MAINVIEW_ACTION, " × ");
                case 4 -> mainView.showTable(MAINVIEW_ACTION, " ÷ ");
            }

            mainView.showTable(MAINVIEW_SECONDNUMBER, model.getSecond());

            if (model.memory(3).equals("0")) {
                mainView.showTable(MAINVIEW_MEMO, "invisible");
            } else {
                mainView.showTable(MAINVIEW_MEMO, "visible");
            }
        });
    }
}