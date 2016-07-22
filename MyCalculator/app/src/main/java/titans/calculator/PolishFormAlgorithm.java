package titans.calculator;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

public class PolishFormAlgorithm implements PolishFormInterface {

    private ArrayList<String> mPolishForm;

    public PolishFormAlgorithm() {
        mPolishForm = new ArrayList<>();
    }

    /**
     * Called by onClick method if user press "=" button.ó
     * @param components Contains the operators and numbers in a row.
     * @return Returns a double that calculated from an equation
     * it will be written to the result @link{TextView}.
     */
    @Override
    public double reversePolishForm(ArrayList<String> components) {
        if(components.isEmpty()) {
            return 0;
        }

        Stack<Double> stack = new Stack<>();
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
     * is the equation in the normal form. The toPolishForm method shapes the
     * equation in polish form and saves the arguments (in String form) in ArrayList
     * @param equationString The equation in normal form.
     * @return an ArrayList
     */
    @Override
    public ArrayList<String> toPolishForm(String equationString) {
        mPolishForm.clear();

        Stack<Character> operators = new Stack<>();
        String number = "";

        int i = 0;

        while(i < equationString.length()){

            Character character = equationString.charAt(i);

            if(character == '+' || character == '-') {
                number = addNumberToList(number);

                if(operators.isEmpty()) {
                    operators.push(character);
                } else {
                    while(!operators.isEmpty()) {
                        addLastOperator(operators);
                    }
                    operators.push(character);
                }
            } else if (character == '*' || character == '/') {
                number = addNumberToList(number);

                if (operators.isEmpty()) {
                    operators.push(character);
                } else {
                    while(!operators.isEmpty() && (operators.peek() != '+' && operators.peek() != '-')) {
                        addLastOperator(operators);
                    }
                    operators.push(character);
                }
            } else {
                number += character;
            }
            ++i;
        }

        if (!number.equals("")) {
            mPolishForm.add(number);
        }

        while(!operators.isEmpty()){
            addLastOperator(operators);
        }

        return mPolishForm;
    }

    private void addLastOperator(Stack<Character> operators){
        Character stackCharacter = operators.pop();
        mPolishForm.add(stackCharacter.toString());
    }

    @NonNull
    private String addNumberToList(String number){
        mPolishForm.add(number);
        return "";
    }
}
