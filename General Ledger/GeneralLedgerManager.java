import java.util.*;
import java.util.Scanner;

/**
 * GeneralLedgerManager class provides an interface for managing all transactions.
 * Name: Yash Sanghvi
 * Email: yash.sanghvi@stonybrook.edu
 * SBU ID: 116203702
 */

public class GeneralLedgerManager {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        GeneralLedger ledger = new GeneralLedger();
        GeneralLedger backupLedger = null;

        printOptions();
        while(true) {
            System.out.println("Enter a selection: ");
            String selection = input.next().toUpperCase();

            switch (selection) {
                case "A":
                    System.out.println("Enter Date: ");
                    String date = input.next();
                    System.out.println("Enter Amount ($): ");
                    double amount;
                    try{
                        amount = input.nextDouble();
                    }catch(Exception e) {
                        System.out.println("Please enter a valid number for amount");
                        input.nextLine();
                        continue;
                    }
                    input.nextLine();
                    System.out.println("Enter Description: ");
                    String description = input.nextLine();
                    Transaction newTransaction = new Transaction(date, amount, description);
                    try{
                        ledger.addTransaction(newTransaction);
                        System.out.println("Transaction successfully added to the general ledger.");
                    }catch(FullGeneralLedgerException | TransactionAlreadyExistsException |
                           InvalidTransactionException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "R":
                    System.out.println("Enter Position: ");
                    int position;
                    try{
                        position = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid position number");
                        input.nextLine();
                        continue;
                    }
                    try{
                        ledger.removeTransaction(position);
                        System.out.println("Removed transaction successfully");
                    }catch(InvalidLedgerPositionException e){
                        System.out.println("Transaction not removed: No such transaction in the general ledger");
                    }
                    break;
                case "G":
                    System.out.println("Enter Position: ");
                    int getPosition;
                    try{
                        getPosition = input.nextInt();
                        input.nextLine();
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid position number");
                        input.nextLine();
                        continue;
                    }
                    try{
                        ledger.getTransaction(getPosition - 1);
                    }catch(InvalidLedgerPositionException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "F":
                    System.out.println("Enter Date: ");
                    String getDate = input.next();
                    GeneralLedger.filter(ledger,getDate);
                    break;
                case "L":
                    System.out.println("Enter Date: ");
                    String existsDate = input.next();
                    System.out.println("Enter Amount: ");
                    double existsAmount = input.nextDouble();
                    input.nextLine();
                    System.out.println("Enter Description");
                    String existsDescription = input.nextLine();
                    Transaction transactionExists = new Transaction(existsDate, existsAmount, existsDescription);
                    try{
                        if(!ledger.exists(transactionExists)){
                            System.out.println("Transaction not found in the general ledger");
                        }
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "S":
                    System.out.println("There are " + ledger.size() + " transactions currently in the general ledger");
                    break;
                case "P":
                    ledger.printAllTransactions();
                    break;
                case "B":
                    backupLedger = (GeneralLedger) ledger.clone();
                    System.out.println("Created a backup of the current general ledger.");
                    break;
                case "CB":
                    if (backupLedger == null) {
                        System.out.println("No backup exists.");
                    } else if (ledger.equals(backupLedger)) {
                        System.out.println("The current general ledger is the same as the backup copy.");
                    } else {
                        System.out.println("The current general ledger is not the same as the backup copy.");
                    }
                    break;
                case "PB":
                    if (backupLedger == null){
                        System.out.println("No backup exists.");
                    }else{
                        backupLedger.printAllTransactions();
                    }
                    break;
                case "RB":
                    if(backupLedger == null){
                        System.out.println("No backup exists.");
                    }else{
                           ledger = (GeneralLedger) backupLedger.clone();
                           System.out.println("General ledger successfully reverted to the backup copy.");
                    }
                    break;
                case "PF":
                    double totalCreditAmount = ledger.getTotalCreditAmount();
                    double totalDebitAmount = ledger.getTotalDebitAmount();
                    System.out.printf("Assets: $%.2f%n",totalCreditAmount);
                    System.out.printf("Liabilities: $%.2f%n",totalDebitAmount);
                    System.out.printf("Net Worth: $%.2f%n",(totalCreditAmount - totalDebitAmount));
                    break;
                case "Q":
                    System.out.println("Program terminating successfully...");
                    input.close();
                    return;
            }
        }


    }

    private static void printOptions() {
        System.out.println("(A) Add Transaction");
        System.out.println("(G) Get Transaction");
        System.out.println("(R) Remove Transaction");
        System.out.println("(P) Print Transactions in General Ledger");
        System.out.println("(F) Filter by Date");
        System.out.println("(L) Look for Transaction");
        System.out.println("(S) Size");
        System.out.println("(B) Backup");
        System.out.println("(PB) Print Transactions in Backup");
        System.out.println("(RB) Revert to Backup");
        System.out.println("(CB) Compare Backup with Current");
        System.out.println("(PF) Print Financial Information");
        System.out.println("(Q) Quit");
    }
}