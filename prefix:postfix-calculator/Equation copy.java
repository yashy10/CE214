/**
 *
 * This class stores the actual equation as a String (in in-fix notation),
 * pre- and post-fix notations as separate Strings, the answer in decimal (base 10) as a double,
 * the answer in binary (base 2) as a String, the answer in hex (base 16) as a String,
 * and a boolean balanced flag to denote whether the equation is balanced or not.
 *
 * author: Yash Sanghvi
 *  * email: yash.sanghvi@stonybrook.edu
 *  * Stony Brook ID: 116203702
 */


import java.util.Stack;

public class Equation {

    /**
     * Member Variables initialization
     */

    private String equation;
    private String prefix;
    private String postfix;
    private double answer;
    private String binary;
    private String hex;
    private boolean balanced;

    /**
     * Default constructor for Equation.
     */

    public Equation(){
    }

    /**
     *
     * Creates an Equation Constructor that stores Parameters
     *
     * @param equation initializes equation
     * @param prefix initializes prefix
     * @param postfix initializes postfix
     * @param answer initializes answer
     * @param binary initializes binaru
     * @param hex initializes hex
     */
    public Equation(String equation, String prefix, String postfix, double answer, String binary, String hex){
        this.equation = equation;
        this.prefix = prefix;
        this.postfix = postfix;
        this.answer = answer;
        this.binary = binary;
        this.hex = hex;


    }

    /**
     * Determines if the equation is balanced or not.
     * This means that the equation has properly matched parentheses.
     * @return true if the equation is balanced, false otherwise.
     */

    public boolean isBalanced(){
        if (equation == null) {
            balanced = false;
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < equation.length(); i++){
            char x = equation.charAt(i);

            if(x == '('){
                stack.push('(');
            } else if (x == ')') {
                if(stack.isEmpty()){
                    return false;
                }
                stack.pop();
            }
        }
        balanced = stack.isEmpty();
        return balanced;
    }

    /**
     * Converts number from decimal (base 10) to binary (base 2) and returns that value.
     * @param number integer value to convert
     * @return decimal number to binary
     * @throws NotBalancedException if parentheses is not balanced throws exception
     */

    public String decToBin(int number) throws NotBalancedException{
        if(isBalanced()) {
            if(number == 0){
                return "0";
            }
            String binary = "";
            while (number > 0){
                int remainder = number % 2;
                binary = remainder + binary;
                number /= 2;
            }
            return binary;
        }else{
            throw new NotBalancedException("Equation is not Balanced");
        }
    }

    /**
     *  Converts postfix answer into decimal
     * @param equation takes in equation
     * @return decimal of equation
     */

    public double getPostfixAnswer(String equation){
        if (equation == null || equation.isEmpty()) {
            throw new IllegalArgumentException("Equation cannot be null or empty.");
        }

        Stack<Double> stack = new Stack<>();

        for(int i = 0; i < equation.length(); i++){
            char ch = equation.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if(Character.isDigit(ch)){
                StringBuilder numEval = new StringBuilder();
                while (i < equation.length() &&
                        (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    numEval.append(equation.charAt(i));
                    i++;
                }
                stack.push(Double.parseDouble(numEval.toString()));
                continue;
            }
            if(isOperator(ch)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = 0;
                if (ch == '+') {
                    result = a + b;
                } else if (ch == '-') {
                    result = a - b;
                } else if (ch == '*') {
                    result = a * b;
                } else if (ch == '/') {
                    result = a / b;
                } else if (ch == '%') {
                    result = a % b;
                } else if (ch == '^') {
                    result = Math.pow(a, b);
                }

                stack.push(result);
            }
            i++;
        }

        answer = stack.peek();
        return answer;
    }

    /**
     * Converts number from decimal (base 10) to hexadecimal (base 16) and returns that value.
     * @param number integer value to convert
     * @return decimal number to hexadecimal
     * @throws NotBalancedException if parentheses is not balanced throws exception
     */

    public String decToHex(int number) throws NotBalancedException{
        if(isBalanced()) {
            if(number == 0){
                return "0";
            }
            String hexidecimal = "";
            int value = 0;
            while (number > 0){
                value = number%16;
                String hexValue;
                switch(value){
                    case 10:
                        hexValue = "A";
                        break;
                    case 11:
                        hexValue = "B";
                        break;
                    case 12:
                        hexValue = "C";
                        break;
                    case 13:
                        hexValue = "D";
                        break;
                    case 14:
                        hexValue = "E";
                        break;
                    case 15:
                        hexValue = "F";
                        break;
                    default:
                        hexValue = Integer.toString(value);
                }
                hexidecimal = hexValue + hexidecimal;
                number = number/16;
            }
            return hexidecimal;
        }else{
            throw new NotBalancedException("Equation is not Balanced");
        }
    }

    /**
     * Converts equation to prefix form
     * @param s takes in equation to convert
     * @return prefix form of equation
     */
    public String preFix(String s){
        Stack<Character> opStack = new Stack<>();
        StringBuilder prefix = new StringBuilder();

        StringBuilder reversed = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '(') {
                reversed.append(')');
            } else if (ch == ')') {
                reversed.append('(');
            } else {
                reversed.append(ch);
            }
        }

        for(int i = 0; i < reversed.length(); i++){
            char ch = reversed.charAt(i);

            if(Character.isWhitespace(ch)){ //skips whitespace
                continue;
            }
            if (Character.isDigit(ch)) {
                StringBuilder number = new StringBuilder();
                while (i < reversed.length() && Character.isDigit(reversed.charAt(i))) {
                    number.append(reversed.charAt(i));
                    i++;
                }
                i--;
                prefix.insert(0, number.reverse().toString() + " ");
            }
            else if (ch == '('){ // ( add to stack
                opStack.push(ch);
            }else if (ch == ')') { // ) remove from stack
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    prefix.insert(0, opStack.pop() + " ");
                }

                if (!opStack.isEmpty()) {
                    opStack.pop();
                }
            }else if (isOperator(ch)) {
                while (!opStack.isEmpty() && precedence(String.valueOf(opStack.peek())) >= precedence(String.valueOf(ch))) {
                    prefix.insert(0, opStack.pop() + " ");
                }
                opStack.push(ch);
            }
        }
        while(!opStack.isEmpty()){
            prefix.insert(0, opStack.pop() + " ");

        }
        return prefix.toString().trim();
    }

    /**
     *  Converter method from infix to postfix
     * @param s takes in equation
     * @return postfix form of equation
     */
    public String postFix(String s){
        Stack<Character> opStack = new Stack<>();

        StringBuilder postfix = new StringBuilder();

        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);

            if(Character.isWhitespace(ch)){ //skips whitespace
                continue;
            }
            if(Character.isDigit(ch)){ //checks if digit and adds to postfix
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    postfix.append(s.charAt(i));
                    i++;
                }
                postfix.append(" ");
                i--;
            }
            else if (ch == '('){ // ( add to stack
                opStack.push(ch);
            }else if (ch == ')'){ // ) remove from stack
                while (!opStack.isEmpty() && opStack.peek() != '('){
                    postfix.append(opStack.pop()).append(" ");
                }
                opStack.pop();
            }else if(isOperator(ch)){
                while(!opStack.isEmpty() && precedence(String.valueOf(opStack.peek())) >= precedence(String.valueOf(ch)) && !isRightAssociative(ch)){
                    postfix.append(opStack.pop()).append(" ");
                }
                opStack.push(ch);
            }
        }

        while(!opStack.isEmpty()){
            postfix.append(opStack.pop()).append(" ");

        }

        return postfix.toString().trim();
    }

    //returns true if ^ is right associative
    private boolean isRightAssociative(char ch) {
        return ch == '^';
    }
    //Checks if input is operator
    private boolean isOperator(char ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }
    //Creates precedence values for easy comparisons
    private int precedence(String precedence) {
        if (precedence.equals("^")){
            return 3;
        }
        if (precedence.equals("*") || precedence.equals("/")) {
            return 2;
        }
        if (precedence.equals("+") || precedence.equals("-")) {
            return 1;
        }
        if(precedence.equals("%")) {
            return 0;
        }
        return -1;
    }

    /**
     * @return Returns a String which is a neat, tabular representation of this Equation.
     */
    public String toString() {
        return  "Equation: " + getEquation() + "\n" +
                "Prefix: "   + getPrefix()   + "\n" +
                "Postfix: "  + getPostfix()  + "\n" +
                "Answer: "   + getAnswer()   + "\n" +
                "Binary: "   + getBinary()   + "\n" +
                "Hex: "      + getHex()      + "\n" +
                "Balanced: " + isBalanced();
    }

    /**
     * Getter/Setter members
     * @return respective values
     */

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public boolean getBalanced() {
        return balanced;
    }



}
