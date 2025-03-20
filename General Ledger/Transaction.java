import java.util.*;

/**
 * This fully-documented class named Transaction which contains the date (String), amount (double) , and description (String)
 * Transaction class is immutable so accessor methods will be provided
 */
public class Transaction implements Cloneable{
    private String date;
    private double amount;
    private String description;

    /**
     * Default constructor for Transaction.
     */

    public Transaction () {
    }

    /**
     * Transaction Constructor that takes parameters
     *
     * @param date          The date of the transaction in yyyy/mm/dd format.
     * @param amount        Transaction amount(positive for debit, negative for credit).
     * @param description   Description of transaction.
     */
    public Transaction (String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * Checks if date is in valid format
     *
     * @param date  The date entry that needs to be validated.
     * @return      True/False - Valid or Invalid
     */

    public boolean isValidDate(String date) {
        if (date == null || !date.matches("\\d{4}/\\d{2}/\\d{2}")) {
            return false;
        }
        String[] parts = date.split("/");
        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            if (year < 1900 || year > 2050) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 30) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Your getter method to return date
     * @return The date in yyyy/mm/dd format
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Returns amount of this transaction.
     *
     * @return Transaction amount.
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * Returns transaction description
     *
     * @return Transaction description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Creates deep copy of Transaction.
     *
     * @return A new transaction object identical to this one.
     */
    public Object clone(){
        Transaction newTransaction = new Transaction(this.date,this.amount,this.description);
        return newTransaction;
    }
    /**
     * Compares this transaction with another object using equals.
     *
     * @param obj The object to compare with.
     * @return true if they have the same date, amount, and description.
     */
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if (obj instanceof Transaction) {
            Transaction that = (Transaction)obj;
            return that.getDate().equals(this.getDate()) &&
                    that.getAmount() == this.getAmount() &&
                    that.getDescription().equals(this.getDescription());
        }
        return false;
    }
}
