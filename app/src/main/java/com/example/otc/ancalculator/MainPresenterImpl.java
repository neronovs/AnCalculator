package com.example.otc.ancalculator;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;


class MainPresenterImpl
        extends MvpBasePresenter<MainView>
        implements MainPresenter {

    private final String SHARED_NAME = "calculator_shared";

    private Model model;

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    ;

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
                model.memory(3);
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
        getView().showTable(model.getTableInfo());
    }

    private void buttonNumberTreatment(int num) {
        //In case that action was not set
        if (model.getAction() < 1 || model.getAction() > 4) {
            if (num == 10)
                model.setFirst(".");
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
            if (num == 10)
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

    private boolean checkNotDoubleZeroInBeginning(String textNumber) {
        return !textNumber.equals("0");
    }

    private void buttonActionTreatment(int num) {
        //If we have a result and press an action btn than the result will be a FirstNumber
//        if (!model.getTableInfo().equals("")
//                && !model.getFirst().equals("")
//                && !model.getSecond().equals("")
//                && model.getAction()==0) {
//            String temp = model.getTableInfo();
//            model.clear();
//            model.setFirst(temp);
//        } else
        if (!model.getTableInfo().equals("") && model.getFirst().equals("")) {
            model.setFirst(model.getTableInfo());
        }

        //1 - plus, 2 - minus, 3 - multiply, 4 - divide, 5 - equal, 10 - dot
        switch (num) {
            case 1:
                model.setAction(1);
                break;
            case 2:
                model.setAction(2);
                break;
            case 3:
                model.setAction(3);
                break;
            case 4:
                model.setAction(4);
                break;
            case 5:
                model.compute(true);
//                getView().showTable(model.getTableInfo());
                break;
            case 10:
                buttonNumberTreatment(10);
                break;
        }
        getView().showTable(model.getTableInfo());
    }

    @Override
    public void showResult(String text) {
        getView().showTable(text);
    }
}