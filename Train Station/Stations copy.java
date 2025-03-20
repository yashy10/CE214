/**
 * Station Class contains a list of Track objects (which are themselves lists of Train objects)
 * that the user can switch between in the menu.
 * It also contains the main method that allows the user to interact with the Trains in the Tracks
 * using specific commands.
 *
 * author: Yash Sanghvi
 * email: yash.sanghvi@stonybrook.edu
 * Stony Brook ID: 116203702
 */

public class Stations {

    /**
     * Member Variables initialization
     */
    private Track head;
    private Track tail;
    private Track cursor;
    private int length = 0;

    /**
     * Default constructor for Stations.
     * Initializes head,tail,cursor to null
     */
    public Stations(){
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }

    /**
     * Adds newTrack to this Station.
     * The new Track object is inserted into the list such that the list is sorted by track numbers.
     * Ensures that the Track object in the Station is unique.
     *
     * @param newTrack Track needed to add
     */
    public void addTrack(Track newTrack){

        if(head == null){
            head = newTrack;
            tail = newTrack;
            cursor = newTrack;
        }else if(newTrack.getTrackNumber() < head.getTrackNumber()) {
            newTrack.setNext(head);
            head.setPrev(newTrack);
            head = newTrack;
        }else{
            Track temp = head;
            while (temp.getNext() != null && newTrack.getTrackNumber() > temp.getNext().getTrackNumber()) {
                temp = temp.getNext();
            }
            newTrack.setNext(temp.getNext());
            newTrack.setPrev(temp);
            if (temp.getNext() != null) {
                temp.getNext().setPrev(newTrack);
            } else {
                tail = newTrack;
            }
            temp.setNext(newTrack);
        }
        cursor = newTrack;
        length++;
    }

    /**
     * Ensures Track is a newTrack
     *
     * @param newTrack Track needed to be added
     * @return true if newTrack is new, false if not
     */
    public boolean checkTrack(Track newTrack){
        Track temp = head;
        while(temp != null){
            if(temp.equals(newTrack)){
                System.out.println("Track not added: Track 1 already exists.");
                return false;
            }
            temp = temp.getNext();
        }
        return true;
    }

    /**
     * Removes the currently selected track from the station.
     *
     * @return the removed Track object
     * @throws IllegalStateException throws if no track is selected
     */
    public Track removeSelectedTrack() throws IllegalStateException {
        if (cursor == null) {
            throw new IllegalStateException("No track selected to remove.");
        }

        Track removeTrack = cursor;

        if (head == tail) {
            head = null;
            tail = null;
            cursor = null;
        } else if (cursor == head) {
            head = head.getNext();
            head.setPrev(null);
            cursor = head;
        } else if (cursor == tail) {
            tail = tail.getPrev();
            tail.setNext(null);
            cursor = tail;
        } else {
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();  // Move cursor forward
        }

        length--;
        return removeTrack;
    }

    /**
     * Prints only selected Track
     */
    public void printSelectedTrack() {
        System.out.println(this.toString());
    }

    /**
     * Prints all Tracks in Station
     */
    public void printAllTracks(){

        if (head == null) {
            System.out.println("No Tracks to print");
            return;
        }
        Track temp = head;
        while (temp != null) {
            String findTrack = (temp == cursor) ? "*" : "";
            System.out.printf("Track %d%s (%.2f%% Utilization Rate):\n",
                    temp.getTrackNumber(), findTrack, temp.getUtilizationRate() * 100);
            System.out.printf("%-10s %-15s %-25s %-15s %-15s\n",
                    "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time");
            System.out.println("-------------------------------------------------------------------------------------");

            Train trainTemp = temp.getHead();
            while (trainTemp != null) {
                String selected = (trainTemp == temp.getCursor()) ? "*" : "";
                System.out.printf("%-10s %-15d %-25s %-15s %-15s\n", selected, trainTemp.getTrainNumber(),
                        trainTemp.getDestination(), String.format("%04d", trainTemp.getArrivalTime()),
                        String.format("%04d", trainTemp.getArrivalTime() + trainTemp.getTransferTime()));
                trainTemp = trainTemp.getNext();
            }

            System.out.println();
            temp = temp.getNext();
        }
    }

    /**
     * Changes Track to given input
     * @param trackToSelect Track wanted to select
     * @return True if selection is successful, false if track not found.
     */
    public boolean selectTrack(int trackToSelect){
        Track temp = head;
        while (temp != null){
            if(trackToSelect == temp.getTrackNumber()){
                cursor = temp;
                return true;
            }
            temp = temp.getNext();
        }
        System.out.println("Track No. " + trackToSelect + " not found.");
        return false;
    }

    /**
     * Prints all info in Station
     */
    public void printStationInformation() {
        if (head == null) {
            System.out.println("Station (0 tracks):");
            return;
        }

        System.out.println("Station (" + length + " tracks):");

        Track temp = head;
        while (temp != null) {
            System.out.printf("    Track %d: %d trains arriving (%.2f%% Utilization Rate)\n",
                    temp.getTrackNumber(), temp.getTrainCount(), temp.getUtilizationRate() * 100);
            temp = temp.getNext();
        }
    }

    /**
     * @return Prints Selected Track
     */
    public String toString() {
        if (cursor == null) {
            return ("No track selected.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Track %d (%.2f%% Utilization Rate):\n",
                cursor.getTrackNumber(), cursor.getUtilizationRate() * 100));
        sb.append(String.format("%-10s %-15s %-25s %-15s %-15s\n",
                "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time"));
        sb.append("-------------------------------------------------------------------------------------\n");

        Train trainTemp = cursor.getHead();
        while (trainTemp != null) {
            String selected = (trainTemp == cursor.getCursor()) ? "*" : "";
            sb.append(String.format("%-10s %-15d %-25s %-15s %-15s\n",
                    selected, trainTemp.getTrainNumber(), trainTemp.getDestination(),
                    String.format("%04d", trainTemp.getArrivalTime()),
                    String.format("%04d", trainTemp.getArrivalTime() + trainTemp.getTransferTime())));
            trainTemp = trainTemp.getNext();
        }

        return sb.toString();
    }

    /**
     * Creates Getter and setters to access variables
     */
    public int getLength() {
        return length;
    }

    public Track getHead() {
        return head;
    }

    public Track getTail() {
        return tail;
    }

    public Track getCursor() {
        return cursor;
    }
}