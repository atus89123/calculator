package com.example.totha.mycalculator;

        import org.junit.Test;

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
    public void polishForm(){
        MainActivity activity = new MainActivity();
        assertTrue("Hibas", activity.polishForm("123+123").toString().equals("[123, 123, +]"));
        assertTrue("Hibas2", activity.polishForm("123-23-2+11*20/10").toString().equals("[123, 23, -, 2, -, 11, 20, *, 10, /, +]"));
    }
}