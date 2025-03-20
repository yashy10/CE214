public class Station {
    private Track head;
    private Track tail;
    private Track cursor;
    private int length = 0;

    public Station(){
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }

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

    public Track removeSelectedTrack(){
        if(cursor == null){
            System.out.println("No trained selected");
            return null;
        }
        Track removeTrack = cursor;
        if(cursor == head){
            head = head.getNext();
            if(head != null) {
                head.setPrev(null);
            }else{
                tail = null;
            }
            cursor = head;
        }else if(removeTrack == tail){
            tail = tail.getPrev();
            if(tail != null){
                tail.setNext(null);
            }else{
                head = null;
                cursor = tail;
            }
        }else {
            removeTrack.getPrev().setNext(removeTrack.getNext());
            removeTrack.getNext().setPrev(removeTrack.getPrev());
            cursor = removeTrack.getNext();
        }

        length--;
        return removeTrack;
    }
    public void printSelectedTrack() {
        if (head == null) {
            System.out.println("No tracks to print.");
            return;
        }
        Track temp = head;
        System.out.printf("Track %d (%.2f%% Utilization Rate):\n", temp.getTrackNumber(), temp.getUtilizationRate() * 100);
        System.out.printf("%-10s %-15s %-20s %-15s %-15s\n", "Selected", "Train Number", "Destination", "Arrival Time", "Departure Time");
        System.out.println("-------------------------------------------------------------------------------");
        while (temp != null) {

            Train trainTemp = temp.getHead();
            while (trainTemp != null) {
                String selected = (trainTemp == temp.getCursor()) ? "*" : "";
                System.out.printf("%-10s %-15d %-20s %04d           %04d\n",
                        selected, trainTemp.getTrainNumber(), trainTemp.getDestination(),
                        trainTemp.getArrivalTime(), trainTemp.getArrivalTime() + trainTemp.getTransferTime());
                trainTemp = trainTemp.getNext();
            }
            System.out.println();
            temp = temp.getNext();
        }
    }
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
            System.out.printf("%-10s %-15s %-20s %-15s %-15s\n",
                    "Selected", "Train Number", "Destination", "Arrival Time", "Departure Time");
            System.out.println("-------------------------------------------------------------------------------");

            Train trainTemp = temp.getHead();
            while (trainTemp != null) {
                String selected = (trainTemp == temp.getCursor()) ? "*" : "";
                System.out.printf("%-10s %-15d %-20s %04d           %04d\n",
                        selected, trainTemp.getTrainNumber(), trainTemp.getDestination(),
                        trainTemp.getArrivalTime(), trainTemp.getArrivalTime() + trainTemp.getTransferTime());
                trainTemp = trainTemp.getNext();
            }

            System.out.println();
            temp = temp.getNext();
        }
    }
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

    /*public void printAllTracks(){
        if(head == null){
            System.out.println("No tracks to print");
        }else{
            Track temp = head;
            while (temp != null){
                System.out.println(temp);
                temp = temp.printSelectedTrack();
            }
        }
    }

    public boolean selectTrack(int trackToSelect){
        for(int i = 0; i < trackToSelect; i++){

        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Track %d (%.2f%% Utilization Rate):\n", trackNumber, utilizationRate * 100));
        sb.append(String.format("%-10s %-15s %-20s %-15s %-15s\n", "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time"));
        sb.append("----------------------------------------------------------------------------------\n");

        Track temp = head;
        while (temp != null) {
            String selected = "";
            if (temp == cursor) {
                selected = "*";
            }
            sb.append(String.format("%-10s %-15d %-20s %04d            %04d\n",
                    selected, temp.getTrainNumber(), temp.getDestination(), temp.getArrivalTime(), temp.getArrivalTime() + temp.getTransferTime()));
            temp = temp.getNext();
        }

        return sb.toString();
    }*/

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
