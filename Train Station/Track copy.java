/**
 * This Track Class contains references to the head and tail of a list of Train nodes,
 * as well as a cursor representing the selected Train node.
 * Each track also contains a track number, which are unique among all Track objects.
 * The Train objects stored in the Track are sorted according to the arrival times of the trains.
 *
 * author: Yash Sanghvi
 * email: yash.sanghvi@stonybrook.edu
 * Stony Brook ID: 116203702
 */

public class Track{

    /**
     * Member Variables initialization
     */

    private Train head;
    private Train tail;
    private Train cursor;
    private Track next;
    private Track prev;
    private double utilizationRate;
    private int trackNumber;
    private int length = 0;

    /**
     * Default constructor for Train.
     * Increments length by 1
     */

    public Track(){
        length++;
    }

    /**
     * Constructor initializing Track with its parameters
     * @param trackNumber initializes track number
     */
    public Track(int trackNumber){
        this.head = null;
        this.tail = null;
        this.cursor = null;
        this.trackNumber = trackNumber;
    }

    /**
     *
     * Maintains schedule order while adding train to track.
     * Throws exceptions to make sure train inserted properly
     *
     * @param newTrain Train needed to add
     * @throws IllegalArgumentException Throws exception if duplicate train
     */
    public void addTrain(Train newTrain) throws IllegalArgumentException{
        if (!isTrainSchedulable(newTrain)) {
            throw new IllegalArgumentException("Train not added: There is a Train already scheduled on Track " + trackNumber + " at that time");
        }
        Train temp = head;
        if(head == null) {
            head = newTrain;
            tail = newTrain;
            cursor = newTrain;
        }else if(newTrain.getArrivalTime() < head.getArrivalTime()){
            newTrain.setNext(head);
            head.setPrev(newTrain);
            head = newTrain;
            cursor = tail;
        }else{
            while (temp.getNext() != null && newTrain.getArrivalTime() > temp.getNext().getArrivalTime()) {
                temp = temp.getNext();
            }
            newTrain.setNext(temp.getNext());
            newTrain.setPrev(temp);
            if (temp.getNext() != null) {
                temp.getNext().setPrev(newTrain);
            } else {
                tail = newTrain;
            }
            temp.setNext(newTrain);
        }
        cursor = tail;
        length++;

        utilizationRate += newTrain.getTransferTime();
    }

    /**
     * Ensures Train is a newTrain
     *
     * @param newTrain Train needed to be added
     * @return true if newTrain, false if not
     */

    public boolean checkTrains(Train newTrain){
        Train temp = head;
        while (temp != null) {
            if (newTrain.equals(temp)){
                throw new IllegalArgumentException("Train not added: There is a Train already scheduled on Track " + getTrackNumber() + " at that time!");
            }
            temp = temp.getNext();
        }
        return true;
    }

    /**
     * Prints the currently selected Train
     */

    public void printSelectedTrain(){
        if(cursor == null){
            System.out.println("No train selected");
            return;
        }
        System.out.println("Selected Train:");
        System.out.println("    Train Number: " + cursor.getTrainNumber());
        System.out.println("    Train Destination: " + cursor.getDestination());
        System.out.printf("    Arrival Time: %04d\n", cursor.getArrivalTime());
        System.out.printf("    Departure Time: %04d\n", cursor.getArrivalTime() + cursor.getTransferTime());
    }

    /**
     * Removes the selected Train
     * @return Removed Train object or null depending on outcome
     */

    public Train removeSelectedTrain(){
        if(cursor == null) {
            System.out.println("no selected");
            return null;
        }
        Train removeTrain = cursor;

        if(removeTrain == head){
            head = head.getNext();
            if(head != null) {
                head.setPrev(null);
            }else{
                tail = null;
            }
            cursor = head;
        }else if(removeTrain == tail){
            tail = tail.getPrev();
            if(tail != null){
                tail.setNext(null);
            }else{
                head = null;
                cursor = tail;
            }
        }else {
            removeTrain.getPrev().setNext(removeTrain.getNext());
            removeTrain.getNext().setPrev(removeTrain.getPrev());
            cursor = removeTrain.getNext();
        }
        removeTrain.setNext(null);
        removeTrain.setPrev(null);

        utilizationRate -= removeTrain.getTransferTime();
        length--;
        cursor = tail;
        return removeTrain;
    }

    /**
     * Points cursor to next Train
     * @return true if cursors moved to next, false if not
     */
    public boolean selectNextTrain(){
        if(cursor != null && cursor.getNext() != null){
            cursor = cursor.getNext();
            return true;
        }
        return false;
    }
    /**
     * Points cursor to previous Train
     * @return true if cursors moved to prev, false if not
     */
    public boolean selectPreviousTrain(){
        if(cursor != null && cursor.getPrev() != null){
            cursor = cursor.getPrev();
            return true;
        }
        System.out.println("Please add a selected Train before this one");
        return false;
    }

    //checks if object is equal to track
    public boolean equals(Object obj){
        if(this == obj)return true;
        if (!(obj instanceof Track)) return false;
        Track checkTrack = (Track)obj;
        return this.trackNumber == checkTrack.trackNumber;
    }

    /**
     * Prints Selected Track
     * @return formated selected Track
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        printHeader();
        Train temp = head;
        while (temp != null) {
            String selected = "";
            if (temp == cursor) {
                selected = "*";
            }
            sb.append(String.format("%-10s %-15d %-20s %04d            %04d\n",
                    selected, temp.getTrainNumber(), temp.getDestination(),
                    temp.getArrivalTime(), temp.getArrivalTime() + temp.getTransferTime()));
            temp = temp.getNext();
        }

        return sb.toString();
    }
    //prints head - using method to make code more readable
    public void printHeader(){
        System.out.print(String.format("Track %d (%.2f%% Utilization Rate):\n", trackNumber, getUtilizationRate() * 100));
        System.out.print(String.format("%-10s %-15s %-20s %-15s %-15s\n", "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time"));
        System.out.print("-------------------------------------------------------------------------\n");
    }

    /**
     * getter and setter methods
     * @return member variables
     */
    public Train getHead() {
        return head;
    }

    public void setHead(Train head) {
        this.head = head;
    }

    public Train getTail() {
        return tail;
    }

    public void setTail(Train tail) {
        this.tail = tail;
    }

    public Train getCursor() {
        return cursor;
    }

    public void setCursor(Train cursor) {
        this.cursor = cursor;
    }

    public Track getNext() {
        return next;
    }

    public void setNext(Track next) {
        this.next = next;
    }

    public Track getPrev() {
        return prev;
    }

    public void setPrev(Track prev) {
        this.prev = prev;
    }

    public double getUtilizationRate() {
        return utilizationRate / 1440.0;
    }

    public void setUtilizationRate(double utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    //counts amount of trains in track
    public int getTrainCount() {
        int count = 0;
        Train temp = head;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    /**
     * Makes sure train is not colliding with any others
     * @param newTrain The train to check for scheduling.
     * @return true if the train can be scheduled, false otherwise.
     */
    public boolean isTrainSchedulable(Train newTrain) {
        Train temp = head;

        while (temp != null) {
            // Checks if train number is duplicate
            if (newTrain.getTrainNumber() == temp.getTrainNumber()) {
                System.out.println("Train not added: There is already a Train with that number");
                return false;
            }

            // Checks for time conflicts
            int existingArrival = temp.getArrivalTime();
            int existingDeparture = existingArrival + temp.getTransferTime();
            int newArrival = newTrain.getArrivalTime();
            int newDeparture = newArrival + newTrain.getTransferTime();

            if ((newArrival >= existingArrival && newArrival < existingDeparture) ||
                    (newDeparture > existingArrival && newDeparture <= existingDeparture) ||
                    (newArrival <= existingArrival && newDeparture >= existingDeparture)) {
                return false;
            }
            temp = temp.getNext();
        }
        return true;
    }


}