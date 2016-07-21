package com.example.totha.mycalculator;

import java.util.ArrayList;

/**
 * Created by totha on 2016. 07. 21..
 */
public interface PolishFormInterface {
    public double reversePolishForm(ArrayList<String> components);
    public ArrayList<String> toPolishForm(String equationString);
}
