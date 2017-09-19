package com.example.otc.ancalculator;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


class MainPresenterImpl
        extends MvpBasePresenter<MainView>
        implements MainPresenter {

    private final String SHARED_NAME = "calculator_shared";

    private final String MAINVIEW_TABLE = "mainView_table";
    private final String MAINVIEW_MEMO = "mainView_memo";
    private final String MAINVIEW_ACTION = "mainView_action";
    private final String MAINVIEW_FIRSTNUMBER = "mainView_firstNumber";
    private final String MAINVIEW_SECONDNUMBER = "mainView_secondNumber";

    private Model model;

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private Context context;

    MainPresenterImpl(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        if (model == null) {
            model = new Computer(sharedPreferences);
            model.setModel((Computer) model);
        }
    }

    @Override
    public void buttonPressed(int btn) {
        switch (btn) {
            case R.id.buttonMPlus:
                model.memory(1);
                break;
            case R.id.buttonMMinus:
                model.memory(2);
                break;
            case R.id.buttonMR:
                if (model.getAction() == 0) {
                    model.clearField(true);
                    model.setFirst(model.memory(3));
                } else {
                    model.clearField(false);
                    model.setSecond(model.memory(3));
                }
                break;
            case R.id.buttonMC:
                model.memory(4);
                break;
            case R.id.button0:
                buttonNumberTreatment(0);
                break;
            case R.id.button1:
                buttonNumberTreatment(1);
                break;
            case R.id.button2:
                buttonNumberTreatment(2);
                break;
            case R.id.button3:
                buttonNumberTreatment(3);
                break;
            case R.id.button4:
                buttonNumberTreatment(4);
                break;
            case R.id.button5:
                buttonNumberTreatment(5);
                break;
            case R.id.button6:
                buttonNumberTreatment(6);
                break;
            case R.id.button7:
                buttonNumberTreatment(7);
                break;
            case R.id.button8:
                buttonNumberTreatment(8);
                break;
            case R.id.button9:
                buttonNumberTreatment(9);
                break;
            case R.id.buttonDot:
                buttonActionTreatment(10);
                break;
            case R.id.buttonPlus:
                buttonActionTreatment(1);
                break;
            case R.id.buttonMinus:
                buttonActionTreatment(2);
                break;
            case R.id.buttonMultiply:
                buttonActionTreatment(3);
                break;
            case R.id.buttonDivide:
                buttonActionTreatment(4);
                break;
            case R.id.buttonEqual:
                buttonActionTreatment(5);
                break;
            case R.id.buttonC:
                model.clear();
                break;
        }
        showResult(model.getTableInfo());
    }

    private void buttonNumberTreatment(int num) {
        //In case that action was not set
        if (model.getAction() < 1 || model.getAction() > 4) {
            model.setCurrentFieldFirst(true);
            if (num == 10)
                //If after "-" is going "." than put "0" after "-"
                if (model.getFirst().equals("-")) {
                    model.setFirst("0.");
                } else {
                    model.setFirst(".");
                }
            else if (num == 0) {
                //Checking fot NotDoubleZero in the beginning of a string
                if (!model.getFirst().equals("0")) {
                    model.setFirst(String.valueOf(num));
                } else {
                    if (model.getFirst().length() > 1) {
                        model.setFirst(String.valueOf(num));
                    }
                }
            } else
                model.setFirst(String.valueOf(num));
            //Action was set
        } else {
            model.setCurrentFieldFirst(false);
            if (num == 10)
                if (model.getSecond().equals(""))
                    model.setSecond("0.");
                else
                    model.setSecond(".");
            else if (num == 0) {
                //Checking fot NotDoubleZero in the beginning of a string
                if (!model.getSecond().equals("0")) {
                    model.setSecond(String.valueOf(num));
                } else {
                    if (model.getSecond().length() > 1) {
                        model.setSecond(String.valueOf(num));
                    }
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

        //1 - plus, 2 - minus, 3 - multiply, 4 - divide, 5 - equal, 10 - dot
        switch (num) {
            case 1:
                model.setAction(1);
                break;
            case 2:
                if (model.isCurrentFieldFirst()) {
                    if (model.getFirst().equals("0")) {
                        model.clearField(model.isCurrentFieldFirst());
                        model.setFirst("-");
                    } else
                        model.setAction(2);
                } else {
                    if (model.getSecond().equals("0")) {
                        model.clearField(model.isCurrentFieldFirst());
                        model.setSecond("-");
                    } else
                        model.setAction(2);
                }
                break;
            case 3:
                model.setAction(3);
                break;
            case 4:
                model.setAction(4);
                break;
            case 5:
                model.compute(true);
//                showResult(model.getTableInfo());
                break;
            case 10:
                buttonNumberTreatment(10);
                break;
        }
        showResult(model.getTableInfo());
    }

    @Override
    public void showResult(String text) {
        getView().showTable(MAINVIEW_TABLE, text);

        getView().showTable(MAINVIEW_FIRSTNUMBER, model.getFirst());

        //1 - plus, 2 - minus, 3 - multiply, 4 - divide, 5 - dot
        switch (model.getAction()) {
            case 0:
                getView().showTable(MAINVIEW_ACTION, "");
                break;
            case 1:
                getView().showTable(MAINVIEW_ACTION, " + ");
                break;
            case 2:
                getView().showTable(MAINVIEW_ACTION, " - ");
                break;
            case 3:
                getView().showTable(MAINVIEW_ACTION, " × ");
                break;
            case 4:
                getView().showTable(MAINVIEW_ACTION, " ÷ ");
                break;
        }

        getView().showTable(MAINVIEW_SECONDNUMBER, model.getSecond());

        if (model.memory(3).equals("0"))
            getView().showTable(MAINVIEW_MEMO, "invisible");
        else
            getView().showTable(MAINVIEW_MEMO, "visible");
    }
}