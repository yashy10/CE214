/**
 * This Train Class contains basic information for a train approaching a station.
 * It includes the train number, the train's destination,
 * the arrival time of the train to its track (in 24-hour format between 0000 - 2359),
 * and the transfer time for how long the train waits at the station (in minutes).
 *
 * author: Yash Sanghvi
 *  * email: yash.sanghvi@stonybrook.edu
 *  * Stony Brook ID: 116203702
 */

public class Train {

    /**
     * Member Variables initialization
     */

    private int trainNumber;
    private int arrivalTime;
    private int transferTime;
    private String destination;
    private Train next;
    private Train prev;

    /**
     * Default constructor for Train.
     */

    public Train(){
    }

    /**
     * Constructor initializing the train with its parameters
     *
     * @param trainNumber   initializes train number
     * @param destination   initializes destination
     * @param arrivalTime   initializes arrival time
     * @param transferTime  initializes transfer time
     */
    public Train(int trainNumber, String destination, int arrivalTime, int transferTime){
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.transferTime = transferTime;
    }

    /**
     *  This is your getter and setters for your private data types
     *
     */

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Moves the pointers of train class
     * Prev and Next
     */
    public Train getNext() {
        return next;
    }

    public void setNext(Train nextTrain) {
        this.next = nextTrain;
    }

    public Train getPrev() {
        return prev;
    }

    public void setPrev(Train prevTrain) {
        this.prev = prevTrain;
    }

    /**
     *  Checks the Train objects to see if its equal to one another
     * @param obj object you are comparing
     * @return true if conditions are met, false otherwise
     */
    public boolean equals(Object obj){
        if(this == obj)return true;

        if(obj instanceof Train){
            Train that = (Train)obj;
            return that.getTrainNumber() == this.getTrainNumber()
                    && that.getArrivalTime() == this.getArrivalTime()
                    && that.getTransferTime() == this.getTransferTime()
                    && that.getDestination().equals(this.getDestination());
        }
        return false;
    }

    /**
     * Your toString method that formats variables in an easy to read format
     * @return formated variables
     */

    public String toString() {
        return String.format("%-10d %-20s %04d %04d",
                trainNumber, destination, arrivalTime, arrivalTime + transferTime);
    }


}