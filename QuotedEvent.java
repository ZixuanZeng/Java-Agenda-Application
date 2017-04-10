import java.util.LinkedList;

public class QuotedEvent extends Event implements AttendableEvent {

    private int quota;
    private String time;
    private LinkedList<User> users;

    public QuotedEvent(int quota, int number, String date, String time) {

        super(number, date);

        this.quota = quota;
        this.time = time;
        users = new LinkedList<User>();
    }

    public boolean forUser(int number) {
        for(User attendedUser : users) {
            if (attendedUser.getId()==number)
               return true;
        }
        return false;
    }

    public int getQuota() {
        return quota;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean isQuoted() {
        return true;
    }

    public boolean isQuotaFull() {
        return users.size()==quota;
    }

    public boolean hasDuration() {
         return true;
    }

    public void attend(User user) {
         if (user==null)
              throw new IllegalArgumentException("user cannot be null");

         if (!isQuotaFull()) {
             if (!isAttended(user)) {
                 users.add(user);
             }
         }
         else {
             throw new QuotaFullException(user.getId());
         }
    }

    public boolean isAttended(User user) {
        for(User attendedUser : users) {
            if (attendedUser.equals(user))
               return true;
        }
        return false;
    }
}