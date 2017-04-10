public class Appointment extends Event implements LongEvent, AttendableEvent {

    private User user1;
    private User user2;
    private int duration;
    private String time;

    public Appointment(int number, String date, String time, int duration) {
        super(number, date);
        this.time = time;
        this.duration = duration;
    }

    /**
     * Each appoinment must have duration
     *
     * @return true for this event type
     */
    public boolean hasDuration() {
        return true;
    }

    /**
     * Returns starting time for this appointment
     *
     * @return string representation of start time of appointment
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns duration of this appoinment
     *
     * @return duration of appointment in minutes
     */
    public int getDuration() {
         return duration;
    }

    public void attend(User user) {
        if (user==null)
              throw new IllegalArgumentException("user cannot be null");

        if (user1==null)
            user1 = user;
        else if (user2==null)
            user2 = user;
    }

    public boolean forUser(int number) {         
        return isAttended(new User(number, ""));
    }

    public boolean isAttended(User user) {
        if (user1!=null)
            if(user1.equals(user))
                return true;

        if (user2!=null)
            if(user2.equals(user))
                return true;

        return false;
    }

    public String getUserNames() {
        return user1.getName() + "," + user2.getName();
    }

    public String getUserNames(int number) {
        if (user1.getId()==number)
            return user2.getName();
        else if (user2.getId()==number)
            return user1.getName();
        else
            return "";
    }

    public String getUserIds() {
        return user1.getId() + "," + user2.getId();
    }

    /**
     * Returns type of event
     *
     * @return "APPOINTMENT" for Appointment instance
     */
    @Override
    public String getEventType() {
        return "APPOINTMENT";
    }

    @Override
    public String toString(int number) {
        return getEventType() + " WITH " + getUserNames(number) + " ON " + getDate() + " " + getTime();
    }
}