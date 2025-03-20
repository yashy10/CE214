public class Train {

    private int trainNumber;
    private int arrivalTime;
    private int transferTime;
    private String destination;
    private Train next;
    private Train prev;

    public Train(){
    }

    public Train(int trainNumber, String destination, int arrivalTime, int transferTime){
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.transferTime = transferTime;
    }
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

    //public String toString(){
      //  return "Train Details: " +
       //         "Train Number = " + trainNumber +
       //         "Arrival Time = " + arrivalTime +
       //         "Transfer Time = " + transferTime +
       //         "Destination = " + destination +
       //         "Next = " + next +
        //        "Prev = " + prev;
    //}
    public String toString() {
        return String.format("%-10d %-20s %04d %04d",
                trainNumber, destination, arrivalTime, arrivalTime + transferTime);
    }


}
