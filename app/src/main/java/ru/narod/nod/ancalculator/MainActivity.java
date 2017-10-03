package ru.narod.nod.ancalculator;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import ru.narod.nod.ancalculator.databinding.ActivityMainBinding;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import io.fabric.sdk.android.Fabric;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView, View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
//        setContentView(R.layout.activity_main);

//        setTitle(BuildConfig.PROGRAM_NAME);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setOnClicker();
        presenter.showResult("0");
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void showTable(String view, String text) {
        switch (view) {
            case "mainView_table":
                binding.mainViewTable.setText(text);
                break;
            case "mainView_action":
                binding.mainViewAction.setText(text);
                break;
            case "mainView_firstNumber":
                binding.mainViewFirstNumber.setText(text);
                break;
            case "mainView_secondNumber":
                binding.mainViewSecondNumber.setText(text);
                break;
            case "mainView_memo":
                if (text.equals("visible"))
                    binding.mainViewMemo.setVisibility(View.VISIBLE);
                else
                    binding.mainViewMemo.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        //Pressing animation
        YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(0)
                .playOn(view);

        presenter.buttonPressed(view.getId());
    }

    void setOnClicker() {
        binding.buttonC.setOnClickListener(this);
        binding.buttonMPlus.setOnClickListener(this);
        binding.buttonMMinus.setOnClickListener(this);
        binding.buttonMR.setOnClickListener(this);
        binding.buttonMC.setOnClickListener(this);
        binding.button0.setOnClickListener(this);
        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.buttonDivide.setOnClickListener(this);
        binding.buttonMultiply.setOnClickListener(this);
        binding.buttonMinus.setOnClickListener(this);
        binding.buttonPlus.setOnClickListener(this);
        binding.buttonDot.setOnClickListener(this);
        binding.buttonEqual.setOnClickListener(this);
        binding.buttonBack.setOnClickListener(this);
        binding.buttonReverse.setOnClickListener(this);
        binding.buttonPercent.setOnClickListener(this);
        binding.buttonSQRT.setOnClickListener(this);
        binding.buttonPow.setOnClickListener(this);
    }
}
