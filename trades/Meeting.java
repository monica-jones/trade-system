package trades;

import java.time.LocalDateTime;

/**
 * @author Janine Newton
 */

public class Meeting {
    private Integer lastEditorID; // the userID of the user who last edited time and location
    private Integer numEdits = 0;
    private LocalDateTime time;
    private String location;
    private String status; // can be pending, open, complete1, complete, or incomplete

    /*
    Meeting statuses:
    pending: has not yet been confirmed
    open: has been confirmed - all users agreed
    complete: the meeting occurred and was marked complete by all users
    incomplete: the meeting did not occur and was marked incomplete by any user
     */

    /**
     * Constructor to create a meeting
     * @param lastEditorID the ID of the user who most recently edited the meeting, or 0 if no user has edited the meeting
     * @param time of the meeting
     * @param location of the meeting
     * @param status of the meeting
     */
    public Meeting(int lastEditorID, LocalDateTime time, String location, String status) {
        this.lastEditorID = lastEditorID;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    /**
     * Returns the userID of the user who most recently edited the meeting
     * @return the userID of the user who most recently edited the meeting
     */
    public int getLastEditorID(){
        return this.lastEditorID;
    }

    /**
     * Sets the userID of the user who most recently edited time and location of meeting
     * @param lastEditorID userID of the user who most recently edited time and location of meeting
     */
    public void setLastEditorID(Integer lastEditorID) {
        this.lastEditorID = lastEditorID;
    }

    /**
     * Returns the number of times the meeting has been edited
     * @return the number of times the meeting has been edited
     */
    public Integer getNumEdits() {
        return numEdits;
    }

    /**
     * Sets the number of times the meeting has been edited
     * @param numEdits the number of edits made to the meeting
     */
    public void setNumEdits(Integer numEdits) {
        this.numEdits = numEdits;
    }

    public void increaseNumEdits() {
        this.numEdits++;
    }

    /**
     * Gets the location of the Trade
     * @return the location of the Trade
     */
    public String getLocation(){
        return location;
    }

    /**
     * Sets the location of the Trade
     * @param inputLocation the location of the Trade
     */
    public void setLocation(String inputLocation){
        location = inputLocation;
    }

    /**
     * Gets the time when the Trade is made
     * @return the time when the Trade is made
     */
    public LocalDateTime getTime(){
        return time;
    }

    /**
     * Sets the time when the Trade is made
     *
     */
    public void setTime(LocalDateTime inputTime){
        this.time = inputTime;
    }

    /**
     * gets the status of meeting
     * @return the status of the meeting
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * sets the status of the meeting to a new status
     * @param newStatus sets the meeting status to a new status
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * gets the details if the meeting
     * @return a string of the meeting details
     */
    public String toString() {
        return "Time: " + time + ". Location " + location + ". Status: " + status  ;
    }

//    public boolean acceptedByAllUsers() {
//        Boolean check;
//        if ((this.User1 = true) && (this.User2 =true)){
//           check = true;
//        }
//        else{
//            check = false;
//        }
//        return check;
//    }

//    public void resetAcceptedMap() {
//        for (Integer userID : acceptedMap.keySet()) {
//            acceptedMap.put(userID, 0);
//        }
//    }
//
//    public void completeMeeting(Integer userID) {
//        completeMap.put(userID, 1);
//    }

//    public boolean completedByAllUsers() {
//        for (Integer userID : completeMap.keySet()) {
//            if (completeMap.get(userID) == 0) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public List<Integer> getUsers() {
//        return new ArrayList<>(acceptedMap.keySet());
//    }
}
