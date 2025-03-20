import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main method that allows the user to interact with Trains and Tracks at the Station.
 * Allows users to add, remove, and select trains and tracks, as well as print station information.
 *
 * author: Yash Sanghvi
 * email: yash.sanghvi@stonybrook.edu
 * Stony Brook ID: 116203702
 */

public class Station {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Track newTrack = null;
        Stations station = new Stations();

        menuDisplay();
        while(true){
            System.out.println("Chose an operation: ");
            String operation = input.next().toUpperCase();

            switch (operation){
                case "A":
                    int trainNumber;
                    String destination;
                    int arrivalTime;
                    int transferTime;

                    try {
                        System.out.println("Enter Train Number: ");
                        trainNumber = input.nextInt();
                        input.nextLine();

                        System.out.println("Enter Train destination: ");
                        destination = input.nextLine();

                        System.out.println("Enter train arrival time: ");
                        arrivalTime = input.nextInt();

                        System.out.println("Enter train transfer time");
                        transferTime = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println(e.getMessage());
                        input.nextLine();
                        break;
                    }
                    if(newTrack == null){
                        System.out.println("Train not added: There is no Track to add the Train to");
                        break;
                    }
                    Train temp = newTrack.getHead();
                    while (temp != null) {
                        if (temp.getTrainNumber() == trainNumber) {
                            System.out.println("Train not added: There is already a Train with that number");
                            break;
                        }
                        temp = temp.getNext();
                    }
                    if(!isValidTime(arrivalTime)) {
                        System.out.println("Train not added: Invalid arrival time.");
                        break;
                    }
                    int departureTime;
                    try {
                        departureTime = calculateDepartureTime(arrivalTime, transferTime);
                    }catch(InputMismatchException e){
                        System.out.println(e.getMessage());
                        input.nextLine();
                        break;
                    }
                    if(departureTime > 2359) {
                        System.out.println("Train not added");
                        break;
                    }
                    Train newTrain = new Train(trainNumber, destination, arrivalTime, transferTime);
                    if (!newTrack.isTrainSchedulable(newTrain)) {
                        System.out.println("Train not added: There is a Train already scheduled on Track " + newTrack.getTrackNumber() + " at that time");
                        break;
                    }
                    if(newTrack.checkTrains(newTrain)) {
                        newTrack.addTrain(newTrain);
                        System.out.println("Train No. " + trainNumber + " to " + destination + " added to Track 1.");
                    }
                    break;
                case "N":
                    if(newTrack == null){
                        System.out.println("No track available");
                        break;
                    }
                    if(newTrack.selectNextTrain()) {
                        System.out.println("Cursor has been moved to next train.");
                    }else{
                        System.out.println("Selected train not updated: Already at end of Track list");
                    }
                    break;
                case "V":
                    if(newTrack == null){
                        System.out.println("No track available");
                        break;
                    }
                    if(newTrack.selectPreviousTrain()){
                        System.out.println("Cursor has been moved to previous train.");
                    }else{
                        System.out.println("Selected train not updated: Already at beginning of Track list");
                    }
                    break;
                case "R":
                    if (newTrack == null) {
                        System.out.println("No track available.");
                        break;
                    }
                    Train removedTrain = newTrack.removeSelectedTrain();
                    if (removedTrain != null) {
                        System.out.println("Train No. " + removedTrain.getTrainNumber() + " to "
                                + removedTrain.getDestination() + " has been removed from Track " + newTrack.getTrackNumber());
                    } else {
                        System.out.println("Track is empty: no trains to remove");
                    }
                    break;
                case "P":
                    if(newTrack == null || newTrack.getCursor() == null){
                        System.out.println("Track is empty: No trains to print");
                        break;
                    }
                    newTrack.printSelectedTrain();
                    break;
                case "TA":

                    System.out.println("Enter Track Number: ");
                    int trackNumber;
                    try {
                        trackNumber = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println(e.getMessage());
                        input.nextLine();
                        break;
                    }
                    if (trackNumber < 0) {
                        throw new IllegalArgumentException("Track number must be positive.");
                    }
                    Track track = new Track(trackNumber);
                    if(station.checkTrack(track)) {
                        station.addTrack(track);
                        newTrack = track;
                        System.out.println("Track " + trackNumber + " added to the Station");
                    }
                    break;
                case "TR":
                    if (newTrack == null) {
                        System.out.println("No track exists");
                        break;
                    }
                    Track removedTrack = station.removeSelectedTrack();
                    if (removedTrack != null) {
                        System.out.println("Closed Track " + removedTrack.getTrackNumber());
                        newTrack = station.getCursor();
                    } else {
                        System.out.println("No train removed.");
                    }
                    break;
                case "TS":
                    System.out.println("Enter the Track number: ");
                    int findTrackNumber;
                    try {
                        findTrackNumber = input.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println(e.getMessage());
                        input.nextLine();
                        break;
                    }
                    if(station.selectTrack(findTrackNumber)){
                        System.out.println("Switched to Track " + findTrackNumber);
                    }else{
                        System.out.println("Could not switch tracks: Track " + findTrackNumber + " does not exist.");
                    }
                    break;
                case "TPS":
                    if(newTrack == null){
                        System.out.println("No track available.");
                        break;
                    }
                    station.printSelectedTrack();
                    break;
                case "TPA":
                    if(newTrack == null || newTrack.getCursor() == null){
                        System.out.println("Track doesn't exist.");
                        break;
                    }
                    station.printAllTracks();
                    break;
                case "SI":
                    station.printStationInformation();
                    break;
                case "Q":
                    System.out.println("Terminating program");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid operation. Please try again.");
            }
        }

    }

    /**
     * Displays the menu options for train and track management.
     */
    public static void menuDisplay(){
        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Train Options                | Track Options               |");
        System.out.println("|   A. Add new Train           |   TA. Add Track             |");
        System.out.println("|   N. Select next Train       |   TR. Remove selected Track |");
        System.out.println("|   V. Select previous Train   |   TS. Switch Track          |");
        System.out.println("|   R. Remove selected Train   |   TPS. Print selected Track |");
        System.out.println("|   P. Print selected Train    |   TPA. Print all Tracks     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| Station Options                                            |");
        System.out.println("|   SI. Print Station Information                            |");
        System.out.println("|   Q. Quit                                                  |");
        System.out.println("|------------------------------------------------------------|");
    }

    /**
     * Checks if input is in Valid Time
     * @param time inputted time
     * @return true if time is valid, false otherwise
     */
    public static boolean isValidTime(int time) {
        int hours = time / 100;
        int minutes = time % 100;
        return (hours >= 0 && hours < 24) && (minutes >= 0 && minutes < 60);
    }

    /**
     * Calculates the departure time by using arrival and transfer time
     * @param arrivalTime Arrival time in correct Format
     * @param transferTime Transfer time in correct Format
     * @return the calculated departure time
     */

    public static int calculateDepartureTime(int arrivalTime, int transferTime) {
        int hours = arrivalTime / 100;
        int minutes = arrivalTime % 100;

        minutes += transferTime;
        hours += minutes / 60;
        minutes %= 60;

        return (hours * 100) + minutes;
    }
}