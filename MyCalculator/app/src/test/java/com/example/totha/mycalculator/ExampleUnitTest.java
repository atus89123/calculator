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
    public void reversePolishFormTest(){
        MainActivity activity = new MainActivity();
        ArrayList<String> test= new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("2.5");
        test.add("*");
        test.add("10");
        test.add("/");
        test.add("+");
        assertTrue("Hibas", activity.reversePolishForm( test ) == 1.5);
    }
}