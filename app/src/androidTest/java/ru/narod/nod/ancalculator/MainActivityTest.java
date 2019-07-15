package ru.narod.nod.ancalculator;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 15.09.2017.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;
    private TextView tv = null;
    private View view = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void onCreate() throws Exception {
        //Test of TextView (main table of a calculator)
        view = mActivity.findViewById(R.id.mainView_table);
        assertNotNull(view);
        tv = (TextView) view;
        assertNotEquals(tv.getText(), R.string.test_text_sample);

        mActivity.setOnClicker();

        //Test of TableLayout (a first line base table for buttons)
        view = mActivity.findViewById(R.id.tableLayout_functionalBtns);
        assertNotNull(view);

        //Test of Button "Cancel"
        view = mActivity.findViewById(R.id.buttonC);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Memory +"
        view = mActivity.findViewById(R.id.buttonMPlus);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Memory -"
        view = mActivity.findViewById(R.id.buttonMMinus);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Memory read"
        view = mActivity.findViewById(R.id.buttonMR);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Memory cancel"
        view = mActivity.findViewById(R.id.buttonMC);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of TableLayout (a first line base table for buttons)
        view = mActivity.findViewById(R.id.tableLayout_buttons);
        assertNotNull(view);

        //Test of Button "0"
        view = mActivity.findViewById(R.id.button0);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "1"
        view = mActivity.findViewById(R.id.button1);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "2"
        view = mActivity.findViewById(R.id.button2);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "3"
        view = mActivity.findViewById(R.id.button3);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "4"
        view = mActivity.findViewById(R.id.button4);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "5"
        view = mActivity.findViewById(R.id.button5);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "6"
        view = mActivity.findViewById(R.id.button6);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "7"
        view = mActivity.findViewById(R.id.button7);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "8"
        view = mActivity.findViewById(R.id.button8);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "9"
        view = mActivity.findViewById(R.id.button9);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Divide"
        view = mActivity.findViewById(R.id.buttonDivide);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Multiply"
        view = mActivity.findViewById(R.id.buttonMultiply);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Minus"
        view = mActivity.findViewById(R.id.buttonMinus);
        assertNotNull(view);
        assertTrue(view.isClickable());

        //Test of Button "Plus"
        view = mActivity.findViewById(R.id.buttonPlus);
        assertNotNull(view);
        assertTrue(view.isClickable());
    }

    @Test
    public void createPresenter() throws Exception {
        assertNotNull(mActivity.getPresenter());
    }

    @Test
    @UiThreadTest
    public void showTable() throws Exception {
        mActivity.showTable("mainView_table", String.valueOf(R.string.test_text_sample));
        view = mActivity.findViewById(R.id.mainView_table);
        tv = (TextView) view;
        tv.setText(R.string.test_text_sample);
        assertEquals("01234567.89", tv.getText());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}