package com.example.totha.mycalculator;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void reversePolishFormTest() {
        MainActivity activity = new MainActivity();
        ArrayList<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("2.5");
        test.add("*");
        test.add("10");
        test.add("/");
        test.add("+");
        assertTrue("Hibas", activity.reversePolishForm(test) == 1.5);

        assertTrue("Hibas", activity.polishForm("123+123").toString().equals("[123, 123, +]"));
        assertTrue("Hibas2", activity.polishForm("123-23-2+11*20/10").toString().equals("[123, 23, -, 2, -, 11, 20, *, 10, /, +]"));

    }

    @Test
    public void fullPolishFormTest() {
        MainActivity activity = new MainActivity();
        ArrayList<String> test = new ArrayList<String>();
        String asd = "1.5+5/2*8*1.5-2";
        assertTrue("Hibas", activity.reversePolishForm( activity.polishForm(asd) ) == 29.5);
    }


}