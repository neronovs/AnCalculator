package ru.narod.nod.ancalculator;

import android.content.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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