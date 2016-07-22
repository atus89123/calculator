package titans.calculator;

import java.util.ArrayList;

public interface PolishFormInterface {
    double reversePolishForm(ArrayList<String> components);
    ArrayList<String> toPolishForm(String equationString);
}
