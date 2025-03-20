/**
 *
 * EquationStack is the class that evaluates an Equation to get the answer.
 * Keeps track of the different types of stacks using a variable,
 * depending on whether the stack is to store operators or operands.
 *
 * author: Yash Sanghvi
 *  * email: yash.sanghvi@stonybrook.edu
 *  * Stony Brook ID: 116203702
 */
import java.util.Stack;

public class EquationStack {

    /**
     * Stack Initialization
     */
    private Stack<String> newStack;

    /**
     * Constructor Initialization
     */

    public EquationStack() {
        newStack = new Stack<>();
    }

    /**
     * Allows you to push to stack
     * @param s adds string to stack
     */
    public void push(String s) {
        newStack.push(s);
    }
    /**
     * Allows you to pop to stack if stack is not empty
     */
    public String pop(){
        if(!newStack.isEmpty()){
            return newStack.pop();
        }else{
            System.out.println("Noting in the stack");
            return null;
        }
    }

    /**
     * Allows you to see top element of stack
     * @return the tope element of the stack
     */

    public String peek(){
        if(!newStack.isEmpty()){
            return null;
        }
        return newStack.peek();
    }

    /**
     * Checks if stack isEmpty
     * @return true if empty, false if not
     */

    public boolean isEmpty(){
        return newStack.isEmpty();
    }

    /**
     * Gives size of stack
     * @return size of Stack
     */

    public int size(){
        return newStack.size();
    }

}
