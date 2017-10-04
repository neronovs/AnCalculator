package ru.narod.nod.ancalculator;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by User on 16.09.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ComputerTest {

    private Context context;
    private SharedPreferences sharedPrefs;

    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);

        Mockito.when(context.getSharedPreferences("calculator_shared", Context.MODE_PRIVATE)).thenReturn(sharedPrefs);

        model = new Computer(context.getSharedPreferences("calculator_shared", Context.MODE_PRIVATE));
        model.setModel((Computer) model);

        model.setSharedPreferences(sharedPrefs);
    }

    //Compute addition
    @Test
    public void computeAddition() throws Exception {
        model.setFirst("123.2");
        model.setSecond("321.2");
        model.setAction(1);
        String calc = model.compute(false);
        assertThat(calc, is("444.4"));
    }

    //Compute subtraction
    @Test
    public void computeSubtraction() throws Exception {
        model.setFirst("999.999");
        model.setSecond("888.888");
        model.setAction(2);
        String calc = model.compute(false);
        assertThat(calc, is("111.111"));
    }

    //Compute subtraction
    @Test
    public void computeSubtraction2() throws Exception {
        model.setFirst("1");
        model.setSecond("0.9");
        model.setAction(2);
        String calc = model.compute(false);
        assertThat(calc, is("0.1"));
    }

    //Compute multiplication
    @Test
    public void computeMultiplication() throws Exception {
        model.setFirst("12345679");
        model.setSecond("9");
        model.setAction(3);
        String calc = model.compute(false);
        assertThat(calc, is("111111111"));
    }

    //Compute division
    @Test
    public void computeDivision() throws Exception {
        model.setFirst("295997.0400");
        model.setSecond("0333");
        model.setAction(4);
        String calc = model.compute(false);
        assertThat(calc, is("888.88"));
    }
    @Test
    public void computeDivision2() throws Exception {
        model.setFirst("3915222.93");
        model.setSecond("35.13733546");
        model.setAction(4);
        String calc = model.compute(false);
        assertThat(calc, is("111426.28997742448624475180964675231"));
    }
    @Test
    public void computeDivision3() throws Exception {
        model.setFirst("1");
        model.setSecond("100000");
        model.setAction(4);
        String calc = model.compute(false);
        assertThat(calc, is("0.00001"));
    }

    //Memory Plus
    @Test
    public void memoryPlus() throws Exception {
        Mockito.when(sharedPrefs.getString(anyString(), anyString())).thenReturn("1");
        assertEquals("1", sharedPrefs.getString("calculator_shared", "0"));
    }

    @Test
    public void clear() throws Exception {
        model.setFirst("918273645.5647381901");
        model.setSecond("5647381901.918273645");
        model.setAction(4);
        model.compute(false);
        model.clear();
        assertEquals("", model.getFirst());
        assertEquals("", model.getSecond());
        assertEquals(0, model.getAction());
        assertEquals("0", model.getTableInfo());
    }

    @Test
    public void setTableInfo() throws Exception {
        assertTrue(model.getTableInfo().equals("0"));
        model.setTableInfo(String.valueOf(R.string.test_text_sample));
        assertFalse(model.getTableInfo().isEmpty());
        assertEquals(String.valueOf(R.string.test_text_sample), model.getTableInfo());
    }

    @Test
    public void getTableInfo() throws Exception {
        String example = "-1234567890";
        assertNotNull(model.getTableInfo());
        assertTrue(model.getTableInfo().equals("0"));
        model.setTableInfo(example);
        assertEquals(example, model.getTableInfo());
    }

    @Test
    public void getFirst() throws Exception {
        assertNotNull(model.getFirst());
        assertTrue(model.getFirst().isEmpty());
        model.setFirst("-.0987654321");
        assertFalse(model.getFirst().isEmpty());
        assertEquals("-.0987654321", model.getFirst());
    }

    @Test
    public void setFirst() throws Exception {
        assertNotNull(model.getFirst());
        assertTrue(model.getFirst().isEmpty());
        model.setFirst("-.0987654321");
        assertFalse(model.getFirst().isEmpty());
        assertEquals("-.0987654321", model.getFirst());
    }

    @Test
    public void getSecond() throws Exception {
        assertNotNull(model.getSecond());
        assertTrue(model.getSecond().isEmpty());
        model.setSecond("-987989878888888889999997.0987654321");
        assertFalse(model.getSecond().isEmpty());
        assertEquals("-987989878888888889999997.0987654321", model.getSecond());
    }

    @Test
    public void setSecond() throws Exception {
        assertNotNull(model.getSecond());
        assertTrue(model.getSecond().isEmpty());
        model.setSecond("-987989878888888889999997.0987654321");
        assertFalse(model.getSecond().isEmpty());
        assertEquals("-987989878888888889999997.0987654321", model.getSecond());
    }

    @Test
    public void setAction() throws Exception {
        assertNotNull(model.getAction());
        assertTrue(model.getAction() == 0);
        model.setAction(1);
        assertFalse(model.getAction() == 3);
        assertTrue(model.getAction() == 1);
        assertEquals(1, model.getAction());
    }

    @Test
    public void getAction() throws Exception {
        model.setAction(4);
        assertFalse(model.getAction() == 3);
        assertTrue(model.getAction() == 4);
        assertEquals(4, model.getAction());
    }

    @Test
    public void makePropriateTextForNumberWithPoint() throws Exception {
        model.setFirst("00000200005.000000900000000");
        assertThat(model.getFirst(), is("200005.0000009"));

        model.clearFirstField(true);

        model.setFirst("-00000200005.000000900000000");
        assertThat(model.getFirst(), is("-200005.0000009"));
    }


    @After
    public void tearDown() throws Exception {
        model = null;
        context = null;
    }
}