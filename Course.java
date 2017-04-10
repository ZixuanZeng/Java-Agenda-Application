import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Course extends QuotedEvent implements RepeatedEvent, LongEvent {

    private String description;
    private LinkedList<User> users;

    public Course(String description, int quota, int number, String date, String time) {
        super(quota, number, date, time);

        users = new LinkedList<User>();

        this.description = description;
    }

    /**
     * Returns name of this course
     *
     * @return name of course
     */
    public String getDescription() {
        return description;
    }


    /**
     * Indicates this event has duration
     *
     * @return true, because each course has duration 2 hrs
     */    
    @Override
    public boolean hasDuration() {
        return true;
    }


    /**
     * Retuns duration of this event
     *
     * @return 2 hours (120 minutes)
     */
    public int getDuration() {
        return 120;
    }

    public boolean isAnnual() {
        return false;
    }

    /**
     * Returns type of event
     *
     * @return "COURSE" for Course instance
     */
    @Override
    public String getEventType() {
         return "COURSE";
    }

    public String toString(int number) {
        return description + " ON " + getDate() + " " + getTime();
    }

    @Override
    public boolean isRepeated() {
        return true;
    }

    private String generateNextDate() {
        String newDate = "";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        try {
            cal.setTime(sdf.parse(getDate()));  
            cal.add(Calendar.DATE, 7);

            SimpleDateFormat sdfw = new SimpleDateFormat("dd/M/yyyy", Locale.US);
            Date date = new Date(cal.getTimeInMillis()); // convert to Date
            return sdfw.format(date).toUpperCase();
        }
        catch(ParseException ex) {
            return "";
        }
    }

    public Event generateNext() {
        return new Course(getDescription(), getQuota(), getId(), generateNextDate(), getTime());
    }
}

