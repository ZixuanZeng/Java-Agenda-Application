import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * This class is based class for birthday too,
 * because birthday is anniversary
 */
public class Anniversary extends Event implements RepeatedEvent {

    private String description;
    private User user;

    public Anniversary(String description, User user, int number, String date) {
        super(number, date);
        this.description = description;
        this.user = user;
    }

    /**
     * Returns name of this anniversary
     *
     * @return name of anniversary
     */
    public String getDescription() {
        return description;
    }

    public boolean forUser(int number) {
        return user.getId()==number;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isRepeated() {
        return true;
    }

    @Override
    public String getEventType() {
        return "ANNIVERSARY";
    }

    public boolean isAnnual() {
        return true;
    }

    @Override
    public boolean isAttendable() {
        return false;
    }
    
    @Override
    public String toString(int number) {
        return getEventType() + ": " + description + " " + getDate();
    }


    public String generateNextDate() {
        String newDate = "";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        try {
            cal.setTime(sdf.parse(getDate()));  
            cal.add(Calendar.YEAR, 1);

            SimpleDateFormat sdfw = new SimpleDateFormat("dd/M/yyyy", Locale.US);
            Date date = new Date(cal.getTimeInMillis()); // convert to Date
            return sdfw.format(date).toUpperCase();
        }
        catch(ParseException ex) {
            return "";
        }
    }

    public Event generateNext() {

        return new Anniversary(description, user, getId(), generateNextDate());
    }
}                       