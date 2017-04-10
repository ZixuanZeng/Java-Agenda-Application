import java.util.LinkedList;

public class Meeting extends Appointment implements LongEvent, AttendableEvent {

    private LinkedList<User> users;

    public Meeting(int number, String date, String time, int duration) {
        super(number, date, time, duration);
        users = new LinkedList<User>();
    }
 
    @Override
    public void attend(User user) {
        if (user==null)
            throw new IllegalArgumentException("user cannot be null");

        users.add(user);
    }

    @Override
    public boolean isAttended(User user) {

        for(User attendedUser : users)
            if (attendedUser.equals(user))
                return true;
                 
        return false;
    }

    @Override
    public String getUserNames() {
        String names = "";
        for(User attendedUser : users)
            names += "," + attendedUser.getName();
        return names.substring(1);
    }
    
    @Override 
    public String getUserNames(int number) {
        String names = "";

        for(User attendedUser : users)
            if (attendedUser.getId()!=number)
                names += "," + attendedUser.getName();

        return names.substring(1);
    }

    @Override
    public String getUserIds() {
        String ids = "";
        for(User attendedUser : users)
            ids += "," + attendedUser.getId();
        return ids.substring(1);
    } 

    /**
     * Returns type of event
     *
     * @return "MEETING" for Meeting instance
     */
    @Override
    public String getEventType() {
        return "MEETING";
    }
}