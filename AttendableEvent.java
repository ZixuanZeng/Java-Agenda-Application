public interface AttendableEvent {

    public void attend(User user);

    public boolean isAttended(User user);   

    public String getTime();
}