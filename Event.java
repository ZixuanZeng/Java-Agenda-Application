import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Event implements Comparable<Event> {

    private String date;
    private int number;

    public Event(int number, String date) {
        this.number = number; 
        this.date = date;
    }

    public int getId() {
        return number;
    }

    public String getDate() {
         return date;
    }

    public boolean isAttendable() {
         return true;
    }

    public boolean hasDuration() {
         return false;
    }

    public boolean isQuoted() {
         return false;
    }

    public boolean isEntertaimentEvent() {
        return false;
    }

    public String getEventType() {
        return "Base Event";
    }

    public boolean isRepeated() {
        return false;
    }

    /**
     * Returns string of event,
     * but prints only other attenders.
     *
     * @param number used id of user should npot be printed
     */
    public String toString(int number) {
        return "Not applicable";
    }

    /**
     * Returns starting moment in milliseconds for this event
     *
     * @return starting moment in milliseconds
     */
    public long getStartingMoment() {
        try {
            Calendar cal = Calendar.getInstance();

            if (isAttendable()) {
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
                AttendableEvent attendableEvent = (AttendableEvent)this;
                cal.setTime(sdf.parse(date+" "+attendableEvent.getTime()));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                cal.setTime(sdf.parse(date));
            }

            return cal.getTimeInMillis();
        }
        catch(ParseException ex) {
            return -1;
        }
    }

    /**
     * Returns ending moment in milliseconds for this event
     * This functions is intended only for events with durations, 
     * not for Anniversaries
     *
     * @return ending moment in milliseconds
     */
    public long getEndingMoment() {
        try {
            Calendar cal = Calendar.getInstance();
            if (isAttendable() && hasDuration()) {
                
                LongEvent longEvent = (LongEvent)this;

                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
                AttendableEvent attendable = (AttendableEvent)this;
                cal.setTime(sdf.parse(date+" "+attendable.getTime()));
                cal.add(Calendar.MINUTE, longEvent.getDuration());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                cal.setTime(sdf.parse(date));
            }

            return cal.getTimeInMillis();
        }
        catch(ParseException ex) {
            return -1;
        }
    }

    /**
     * Checks it's event for this user
     *
     * @param user id of user to check
     * @return true, if event is for this user
     */
    public boolean forUser(int number) {
        return false;
    }

    /**
     * Used for sorting events in ascending order
     */
    public int compareTo(Event otherEvent) {
        long moment1 = getStartingMoment();
        long moment2 = otherEvent.getStartingMoment();

        if (isAttendable())
           moment1++;

        if (otherEvent.isAttendable())
           moment2++;

        if (moment1>moment2)       
            return 1; 
        else if (moment1<moment2)       
            return -1; 
        else
            return 0;
    }

    /**
     * Gets day of week of current event
     * Used for cources
      
     */
    public String getDayOfWeek() {

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
            SimpleDateFormat sdfw = new SimpleDateFormat("EEEE", Locale.US);
            cal.setTime(sdf.parse(date));
            Date date = new Date(cal.getTimeInMillis()); // convert to Date

            return sdfw.format(date).toUpperCase();
        }
        catch(ParseException ex) {
            // Will newer be here
            return "???";
        }
    }
}
