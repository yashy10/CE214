/**
 *
 * fully-documented class named HistoryStack that contains the user's history of equations.
 * This is similar to EquationStack, with some extra functionality.
 *
 * author: Yash Sanghvi
 * email: yash.sanghvi@stonybrook.edu
 * Stony Brook ID: 116203702
 */

import java.util.Stack;

public class HistoryStack {

    /**
     * Initializing stacks
     */
    private Stack<Equation> historyStack;
    private Stack<Equation> redoStack;

    /**
     * Constructor to initialize historyStack from calculator
     */
    public HistoryStack(){
        historyStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * adds equation to historyStack
     * @param newEquation pushes equation to newStack
     */
    public void push(Equation newEquation){
        historyStack.push(newEquation);
    }

    /**
     * Looks at top equation in new Stack
     * @return top equation
     */
    public Equation peek(){
        return historyStack.peek();
    }

    /**
     * @return pops top element in stack
     */
    public Equation pop(){
        if(!historyStack.isEmpty()){
            return historyStack.pop();
        } else {
            System.out.println("Nothing in the stack. ");
            return null;
        }
    }

    /**
     * undo recently inserted equation
     * pushes it to redoStack
     */

    Equation undo;
    public void undo(){
        if(!historyStack.isEmpty()){
            undo = historyStack.pop();
            redoStack.push(undo);
        } else {
            System.out.println("Nothing in the stack");
        }
    }

    /**
     * Pushes redo to the historyStack
     */
    public void redo(){
        if(!redoStack.isEmpty()){
            Equation redo = redoStack.pop();
            historyStack.push(redo);
        }else{
            throw new IllegalStateException("No equation to redo.");
        }
    }

    /**
     * looks at top element in redoStack
     * @return top element in redoStack
     */

    public Equation peekRedo() {
        if (!redoStack.isEmpty()) {
            return redoStack.peek();
        } else {
            throw new IllegalStateException("No equation to redo.");
        }
    }
    //returns size of history stack
    public int size(){
        return historyStack.size();
    }

    /**
     * Searches through this HistoryStack and returns the Equation located at the specified position.
     * If the position is out of range or otherwise invalid, throw an appropriate exception.
     * @param position integer position
     * @return equation position
     */
    public Equation getEquation(int position){
        if (position < 0 || position >= historyStack.size()){
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }
        return historyStack.get(position);
    }

    /**
     * @return Returns a neatly formatted table of this HistoryStack.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-4s%-35s%-35s%-35s%-25s%-15s%-15s\n",
                "#", "Equation", "Pre-Fix", "Post-Fix", "Answer", "Binary", "Hexadecimal"));
        sb.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            Equation printEquation = historyStack.get(i);
            sb.append(String.format("%-4d%-35s%-35s%-35s%-25.3f%-15s%-15s\n",
                    i + 1,
                    printEquation.getEquation(),
                    printEquation.getPrefix(),
                    printEquation.getPostfix(),
                    printEquation.getAnswer(),
                    printEquation.getBinary(),
                    printEquation.getHex()));
        }

        return sb.toString();
    }

    /**
     * @return looks at top of toString
     */
    public String peekToString() {
        if (historyStack.isEmpty()) {
            return "No equations in history.";
        }

        Equation printEquation = historyStack.peek();
        StringBuilder sb2 = new StringBuilder();

        sb2.append(String.format("%-4s%-35s%-35s%-35s%-25s%-15s%-15s\n",
                "#", "Equation", "Pre-Fix", "Post-Fix", "Answer", "Binary", "Hexadecimal"));
        sb2.append("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");

        sb2.append(String.format("%-4d%-35s%-35s%-35s%-25.3f%-15s%-15s\n",
                historyStack.size(),
                printEquation.getEquation(),
                printEquation.getPrefix(),
                printEquation.getPostfix(),
                printEquation.getAnswer(),
                printEquation.getBinary(),
                printEquation.getHex()));

        return sb2.toString();
    }


}
