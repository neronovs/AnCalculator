package com.example.otc.ancalculator;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.otc.ancalculator.databinding.ActivityMainBinding;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView, View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setOnClicker();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new mainPresenterImpl(this);
    }

    @Override
    public void showTable(String text) {
        binding.mainViewTable.setText(text);
    }

    @Override
    public void onClick(View view) {
//        presenter.buttonPressed((String) ((AppCompatButton) view).getText());
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
    }
}
