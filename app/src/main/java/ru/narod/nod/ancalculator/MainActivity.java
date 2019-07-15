package ru.narod.nod.ancalculator;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ru.narod.nod.ancalculator.databinding.ActivityMain1Binding;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import io.fabric.sdk.android.Fabric;
import ru.narod.nod.ancalculator.privacy_policy.PrivacyPolicyFragment;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView, View.OnClickListener {

    private ActivityMain1Binding binding;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main1);
        setOnClicker();
        presenter.showResult("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuPrivacyPolicy:
                showPrivacyPolicy();
                return true;
            case R.id.menuClose:
                closePrivacyPolicy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closePrivacyPolicy() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    private void showPrivacyPolicy() {
        PrivacyPolicyFragment fragment = PrivacyPolicyFragment.Companion.newInstance();

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_container, fragment);
        // Add to bakstack
        ft.addToBackStack(fragment.getTag());
        // Complete the changes added above
        ft.commit();

        fragment.setMenu(this.menu);
    }

    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)">onOptionsItemSelected()</a></code>
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
    public void onClick(final View view) {
        //Pressing animation
        runOnUiThread(() -> YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(0)
                .playOn(view));


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
