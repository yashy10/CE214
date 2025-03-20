/**
 *
 * Fully-documented class named Calculator that represents the company's new calculator.
 * This class contains the main method that acts as a driver for the program that the user will interact with.
 *
 * author: Yash Sanghvi
 * email: yash.sanghvi@stonybrook.edu
 * Stony Brook ID: 116203702
 */

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        HistoryStack history = new HistoryStack();
        //The main method that allows the user to use the calculator.
        printMenu();

        while(true){

            System.out.println("Select an option: ");
            String operation = input.nextLine().trim().toUpperCase();
            Equation newEquation = new Equation();

            switch(operation){
                case "A":
                    System.out.println("Please enter an equation (in-fix notation): ");
                    String equation = input.nextLine();

                    newEquation.setEquation(equation);

                    try {
                        if(newEquation.isBalanced()) {
                            String postfix = newEquation.postFix(equation);
                            newEquation.setPostfix(postfix);

                            double result = newEquation.getPostfixAnswer(postfix);
                            newEquation.setAnswer(result);

                            if (Double.isInfinite(result) || Double.isNaN(result)) {
                                throw new ArithmeticException("Invalid result");
                            }

                            newEquation.setPrefix(newEquation.preFix(equation));
                            int rounded = (int) Math.round(result);
                            newEquation.setBinary(newEquation.decToBin(rounded));
                            newEquation.setHex(newEquation.decToHex(rounded));
                            System.out.printf("The equation is balanced and the answer is %.3f%n", result);
                        }else {
                            throw new Exception("The equation is not balanced but saved.");
                        }
                    }catch(Exception e){
                            newEquation.setPostfix("N/A");
                            newEquation.setPrefix("N/A");
                            newEquation.setAnswer(0.0);
                            newEquation.setBinary("0");
                            newEquation.setHex("0");
                            System.out.println("The equation is not balanced but saved.");
                        }
                    history.push(newEquation);
                    break;
                case "P":

                    System.out.println(history.toString());
                    break;
                case "F":
                    System.out.println("Which equation would you like to change?");
                    int eqNumber;
                    try {
                        eqNumber = Integer.parseInt(input.nextLine().trim());
                        System.out.println(" ");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Returning to main menu.");
                        break;
                    }
                    if (eqNumber < 1 || eqNumber > history.size()) {
                        System.out.println("No equation at this position.");
                        break;
                    }

                    int index = history.size() - eqNumber;
                    Equation original = history.getEquation(index);
                    System.out.println("Equation at position " + eqNumber + ": " + original.getEquation());
                    String newEq = original.getEquation(); // start with the original equation string
                    boolean moreChanges = true;
                    while (moreChanges) {
                        System.out.println("What would you like to do to the equation (Replace / remove / add)?");
                        String action = input.nextLine().trim().toLowerCase();
                        switch (action) {
                            case "replace":
                                System.out.println("What position would you like to change?");
                                int repPos;
                                try {
                                    repPos = Integer.parseInt(input.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                if (repPos < 1 || repPos > newEq.length()) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                System.out.println("What would you like to replace it with?");
                                String replacement = input.nextLine();

                                newEq = newEq.substring(0, repPos - 1) + replacement + newEq.substring(repPos);
                                System.out.println(" ");
                                break;
                            case "remove":
                                System.out.println("What position would you like to remove?");
                                int remPos;
                                try {
                                    remPos = Integer.parseInt(input.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                if (remPos < 1 || remPos > newEq.length()) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                newEq = newEq.substring(0, remPos - 1) + newEq.substring(remPos);
                                System.out.println(" ");
                                break;
                            case "add":
                                System.out.println("What position would you like to add something?");
                                int addPos;
                                try {
                                    addPos = Integer.parseInt(input.nextLine().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                if (addPos < 1 || addPos > newEq.length() + 1) {
                                    System.out.println("No equation at this position.");
                                    continue;
                                }
                                System.out.println("What would you like to add?");
                                String addition = input.nextLine();
                                newEq = newEq.substring(0, addPos - 1) + addition + newEq.substring(addPos - 1);
                                System.out.println(" ");
                                break;
                            default:
                                System.out.println("Invalid action. Please enter Replace, remove, or add.");
                                continue;
                        }
                        System.out.println("Equation: " + newEq);
                        System.out.println("Would you like to make any more changes? (Y/YES or N/NO)");
                        String more = input.nextLine().trim().toLowerCase();
                        if (more.equals("n") || more.equals("no")) {
                            moreChanges = false;
                        }
                    }

                    Equation modified = new Equation();
                    modified.setEquation(newEq);
                    try {
                        if (modified.isBalanced()) {
                            String postfix = modified.postFix(newEq);
                            modified.setPostfix(postfix);

                            double result = modified.getPostfixAnswer(postfix);
                            modified.setAnswer(result);

                            if (Double.isInfinite(result) || Double.isNaN(result)) {
                                throw new ArithmeticException("Invalid result");
                            }
                            modified.setPrefix(modified.preFix(newEq));

                            int rounded = (int) Math.round(result);
                            modified.setBinary(modified.decToBin(rounded));
                            modified.setHex(modified.decToHex(rounded));

                            System.out.printf("The equation is balanced and the answer is: %.3f%n", result);
                        } else {
                            throw new Exception("Equation is not balanced.");
                        }
                    } catch (Exception e) {
                        modified.setPostfix("N/A");
                        modified.setPrefix("N/A");
                        modified.setAnswer(0.0);
                        modified.setBinary("0");
                        modified.setHex("0");
                        System.out.println("Equation is not balanced.");
                    }
                    history.push(modified);
                    break;


                case "B":
                    if(history.size() == 0){
                        System.out.println("Calculator History is empty.");
                    }else{
                        System.out.println(history.peekToString());
                    }
                    break;
                case "U":
                    if (history.size() == 0) {
                        System.out.println("No equation to undo.");
                    } else {
                        Equation undoneEquation = history.peek();
                        history.undo();
                        System.out.println("Equation '" + undoneEquation.getEquation() +  "' undone");
                    }
                    break;
                case "R":
                    try{
                        Equation redoEquation = history.peekRedo();
                        history.redo();
                        System.out.println("Redoing equation '" + redoEquation.getEquation() +  "'");
                    } catch(IllegalStateException e){
                        System.out.println("No equation to redo.");
                    }
                    break;
                case "C":
                    while (history.size() != 0){
                        history.pop();
                    }
                    System.out.println("Resetting calculator.");
                    break;
                case "Q":
                    System.out.println("Program terminating normally...");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid operation. Please try again.");
            }

        }


    }
    public static void printMenu() {
        System.out.println("Welcome to calculator.\n");
        System.out.println("[A] Add new equation");
        System.out.println("[F] Change equation from history");
        System.out.println("[B] Print previous equation");
        System.out.println("[P] Print full history");
        System.out.println("[U] Undo");
        System.out.println("[R] Redo");
        System.out.println("[C] Clear history");
        System.out.println("[Q] Quit");
    }

}