/**
 * Sorted by date the General Ledger class stores a list of up to 50 transactions.
 * It allows adding, removing, filtering, and printing transactions while tracking debit and credit values.
 */
public class GeneralLedger{
    static final int MAX_TRANSACTIONS = 50;
    private double totalDebitAmount;
    private double totalCreditAmount;
    private Transaction[] transactions;
    private int addedTransaction = 0;
    /**
     * Empty GeneralLedger Constructor.
     */
    public GeneralLedger(){
        this.transactions = new Transaction[MAX_TRANSACTIONS];
    }
    /**
     * Getter method to access the total debit amount in the ledger.
     *
     * @return The sum of all debit transactions.
     */
    public double getTotalDebitAmount() {
        return this.totalDebitAmount;
    }
    /**
     * Getter method to access the total credit amount in the ledger.
     *
     * @return The sum of all debit transactions.
     */
    public double getTotalCreditAmount(){
        return this.totalCreditAmount;
    }

    /**
     * Allows for initialization of a newTotalDebitAmount.
     *
     * @param newTotalDebitAmount sets new debit amount
     */
    public void setTotalDebitAmount(double newTotalDebitAmount){
        this.totalDebitAmount = newTotalDebitAmount;
    }
    /**
     * Allows for initialization of a newTotalCreditAmount.
     *
     * @param newTotalCreditAmount sets new credit amount
     */
    public void setTotalCreditAmount(double newTotalCreditAmount){
        this.totalCreditAmount = newTotalCreditAmount;
    }

    /**
     * Adds a new transaction to the ledger, maintaining date order.
     *
     * @param newTransaction                        The transaction to add.
     * @throws FullGeneralLedgerException           Thrown if the ledger is full.
     * @throws InvalidTransactionException          Thrown if the transaction amount is 0 or the date is invalid.
     * @throws TransactionAlreadyExistsException    Thrown if a duplicate transaction exists.
     */
    public void addTransaction(Transaction newTransaction) throws FullGeneralLedgerException,
            InvalidTransactionException, TransactionAlreadyExistsException {
        if(addedTransaction>= MAX_TRANSACTIONS){
            throw new FullGeneralLedgerException("General ledger is full");
        }
        if(newTransaction.getAmount() == 0){
            throw new InvalidTransactionException("Transaction amount cannot be 0");
        }else if(!newTransaction.isValidDate(newTransaction.getDate())){
            throw new InvalidTransactionException("Please enter a valid Date");
        }
        if(exists(newTransaction)){
            throw new TransactionAlreadyExistsException("Transaction already exists in ledger");
        }

        //Inserts transaction by date
        int indexPosition = 0;
        for(int findIndex = 0; findIndex < addedTransaction; findIndex++){
            if((transactions[findIndex].getDate().compareTo(newTransaction.getDate()) > 0)){
                indexPosition = findIndex;
                break;
            }else{
                indexPosition = addedTransaction;
            }
        }

        //Shifts transactions forward to make room for new transaction
        if(indexPosition < addedTransaction) {
            for (int i = addedTransaction; i > indexPosition; i--) {
                transactions[i] = transactions[i - 1];
            }
        }
        transactions[indexPosition] = newTransaction;
        addedTransaction++;

        //updates total for debit and credit amounts
        if (newTransaction.getAmount() < 0) {
            totalDebitAmount += Math.abs(newTransaction.getAmount());
        } else {
            totalCreditAmount += newTransaction.getAmount();
        }
    }

    /**
     * Allows you to remove transaction from general ledger
     *
     * @param position Input transaction wanted to be removed
     * @throws InvalidLedgerPositionException throws if position is not valid"
     */
    public void removeTransaction(int position) throws InvalidLedgerPositionException {
        if (position <= 0 || position > addedTransaction) {
            throw new InvalidLedgerPositionException("Position is not valid");
        }
        for (int i = position - 1; i < addedTransaction - 1; i++) {
            transactions[i] = transactions[i+1];
        }
        transactions[addedTransaction-1] = null;
        addedTransaction--;
    }

    public static void printHeader(){
        System.out.println(String.format("%-6s %-12s %-9s %-9s %s", "No.", "Date","Debit","Credit","Description"));
        System.out.println("----------------------------------------------------------------------------------------");
    }

    /**
     * Allows you to get transaction
     *
     * @param position Transaction number you want to print
     * @throws InvalidLedgerPositionException Throws if no such Transaction.
     */

    public void getTransaction(int position) throws InvalidLedgerPositionException{
        if (position < 0 || position >= addedTransaction) {
            throw new InvalidLedgerPositionException("No such Transaction.");
        }
        //formats transaction
        printHeader();
        System.out.printf("%-6d %-12s %-9s %-9s %s\n", position+1, transactions[position].getDate(),
                (transactions[position].getAmount() > 0)
                        ? String.format("%.2f", transactions[position].getAmount()) : "",
                (transactions[position].getAmount() < 0)
                        ? String.format("%.2f", Math.abs(transactions[position].getAmount())) : "",
                transactions[position].getDescription());
    }

    /**
     * Prints the transactions with the date provided
     *
     * @param generalLedger checks if transaction exists
     * @param date compares to transactions
     */
    public static void filter (GeneralLedger generalLedger, String date){
        if(checkFilterDate(generalLedger, date)) {
            printHeader();
            for (int i = 0; i < generalLedger.addedTransaction; i++) {
                if (generalLedger.transactions[i].getDate().equals(date)) {
                    System.out.printf("%-6d %-12s %-9s %-9s %s\n", i + 1, generalLedger.transactions[i].getDate(),
                            (generalLedger.transactions[i].getAmount() > 0)
                                    ? String.format("%.2f", generalLedger.transactions[i].getAmount()) : "",
                            (generalLedger.transactions[i].getAmount() < 0)
                                    ? String.format("%.2f", Math.abs(generalLedger.transactions[i].getAmount())) : "",
                            generalLedger.transactions[i].getDescription());
                }
            }
        }
    }

    /**
     * Makes sure the date requested for filtration exists
     * @param generalLedger checks if transaction exists
     * @param date compares to transactions
     * @return true or false - date is filterable or not
     */
    public static boolean checkFilterDate(GeneralLedger generalLedger, String date) {
        for (int i = 0; i < generalLedger.addedTransaction; i++) {
            if (generalLedger.transactions[i].getDate().equals(date)) {
                return true;
            }
        }
        System.out.println("No transactions found for " + date);
        return false;
    }

    /**
     * Clones General Ledger with all the transactions in the array
     *
     * @return newGeneralLedger with transactions and data types
     */
    public Object clone(){
        GeneralLedger newGeneralLedger = new GeneralLedger();
        for(int i = 0; i < addedTransaction; i++){
            newGeneralLedger.addTransaction((Transaction) transactions[i].clone());
        }
        newGeneralLedger.setTotalCreditAmount(this.totalCreditAmount);
        newGeneralLedger.setTotalDebitAmount(this.totalDebitAmount);

        return newGeneralLedger;
    }

    /**
     * Checks if given transaction already exists in the ledger.
     *
     * @param transaction checks the transaction
     * @return true or false - Exists or doesn't
     * @throws IllegalArgumentException Throws if Transaction is not valid transaction object.
     */

    public boolean exists(Transaction transaction) throws IllegalArgumentException{
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction is not valid transaction object.");
        }
        for(int i = 0; i < addedTransaction; i++){
            if(transactions[i].equals(transaction)){
                getTransaction(i);
                return true;
            }
        }
        return false;
    }

    public int size(){
        return addedTransaction;
    }

    /**
     * Prints all the transactions in the general ledger
     */
    public void printAllTransactions(){
        if(addedTransaction == 0) {
            System.out.println("No transactions currently in the general ledger");
            return;
        }else{
            printHeader();
            System.out.println(this.toString());
        }
    }

    /**
     * Creates a method that can print transactions anywhere
     *
     * @return transaction in printed format
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addedTransaction; i++) {
            sb.append(String.format("%-6d %-12s %-9s %-9s %s\n", i+1, transactions[i].getDate(),
                    (transactions[i].getAmount() > 0)
                            ? String.format("%.2f", transactions[i].getAmount()) : "",
                    (transactions[i].getAmount() < 0)
                            ? String.format("%.2f", Math.abs(transactions[i].getAmount())) : "",
                    transactions[i].getDescription()));
        }

        return sb.toString();
    }

    /**
     * Allows you to compare two objects
     *
     * @param obj takes in the object that wants to be equalized
     * @return true if equal, false if not
     */
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if (!(obj instanceof GeneralLedger)) {
            return false;
        }
        GeneralLedger that = (GeneralLedger) obj;
        if(that.getTotalCreditAmount() != this.getTotalCreditAmount() ||
                that.getTotalDebitAmount() != this.getTotalDebitAmount()) {
            return false;
        }
        for(int i = 0; i < addedTransaction; i++){
            if(!that.transactions[i].equals(this.transactions[i])){
                return false;
            }
        }
        return true;
    }
}
