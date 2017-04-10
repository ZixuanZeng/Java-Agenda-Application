public interface RepeatedEvent {

    public boolean isAnnual();
    public String getDayOfWeek();
    public Event generateNext();
}