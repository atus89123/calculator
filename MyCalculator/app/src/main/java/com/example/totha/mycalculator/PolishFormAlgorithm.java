package com.example.totha.mycalculator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by totha on 2016. 07. 21..
 */
public class PolishFormAlgorithm implements PolishFormInterface {

    private ArrayList<String> polishF;

    public PolishFormAlgorithm() {
        polishF = new ArrayList<String>();
    }

    /**
     * Called by onClick method if user press "=" button
     * Returns a double that calculated from an equation
     * it will be wrote to the reult textview
     * @param components contains the operators and numbers in a row
     * @return calculated result
     */
    @Override
    public double reversePolishForm(ArrayList<String> components) {
        if(components.isEmpty()) return 0;
        Stack<Double> stack = new Stack<>();;
        double num1;
        double num2;

        for(String comp : components){
            if(comp.equals("+")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 + num2);
            }
            else if(comp.equals("-")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 - num2);
            }
            else if(comp.equals("*")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 * num2);
            }
            else if(comp.equals("/")){
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 / num2);
            }
            else {
                stack.push(Double.parseDouble(comp));
            }
        }
        return stack.pop();
    }



    /**
     * Returns an ArrayList object. The equationString argument
     * is the equation in the normal form. The polishForm method shape the
     * equation in polish form and save the arguments (in String form) in ArrayList
     * @param equationString is the equation in normal form
     * @return an ArrayList
     */
    @Override
    public ArrayList<String> toPolishForm(String equationString){
        polishF.clear();
        int i = 0;
        Stack<Character> operators = new Stack<>();
        String number = "";
        while(i < equationString.length()){
            Character character = equationString.charAt(i);
            if(character == '+' || character == '-'){
                number = addNumberToList(number);
                if(operators.isEmpty()) operators.push(character);
                else{
                    while(!operators.isEmpty()){
                        addLastOperator(operators);
                    }
                    operators.push(character);
                }
            } else if(character == '*' || character == '/'){
                number = addNumberToList(number);
                if(operators.isEmpty()) operators.push(character);
                else{
                    while(!operators.isEmpty() && (operators.peek() != '+' && operators.peek() != '-')){
                        addLastOperator(operators);
                    }
                    operators.push(character);
                }
            } else{
                number += character;
            }
            ++i;
        }
        if(!number.equals("")) polishF.add(number);
        while(!operators.isEmpty()){
            addLastOperator(operators);
        }
        System.out.println(polishF.toString());
        return polishF;
    }

    private void addLastOperator(Stack<Character> operators){
        Character stackCharacter = operators.pop();
        polishF.add(stackCharacter.toString());
    }

    @NonNull
    private String addNumberToList(String number){
        polishF.add(number);
        return "";
    }
}
