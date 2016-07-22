package com.example.totha.mycalculator;

import java.util.ArrayList;

public interface PolishFormInterface {
    public double reversePolishForm(ArrayList<String> components);
    public ArrayList<String> toPolishForm(String equationString);
}
