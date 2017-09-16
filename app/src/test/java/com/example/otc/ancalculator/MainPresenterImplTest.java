package com.example.otc.ancalculator;

import android.content.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.R.attr.mode;
import static android.R.attr.targetClass;
import static android.R.attr.value;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by User on 16.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplTest {

    private MainPresenter mPresenter = null;
    private Context context = null;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        context = Mockito.mock((Context.class));
        mPresenter = new MainPresenterImpl(context);
        mPresenter.setModel(mPresenter.getModel());

//        when(mPresenter).thenReturn("Fake name");
    }

    @Test
    public void buttonPressed() throws Exception {
//        mPresenter.buttonPressed(R.id.button0);
//        assertEquals(0, mPresenter.getModel().getAction());
//        assertEquals("0", mPresenter.getModel().getFirst());
//        mPresenter.getModel().setAction(2);
//        mPresenter.buttonPressed(R.id.button1);
//        assertEquals("0", mPresenter.getModel().getFirst());
//        assertEquals("1", mPresenter.getModel().getSecond());
    }

    @Test
    public void showResult() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }
}