public class Concert extends EntertaimentEvent {

    public Concert(String description, int quota, int number, String date, String time) {
        super(description, quota, number, date, time);          
    }

    @Override
    public String getEventType() {
        return "CONCERT";
    }

    /**
     * Retuns duration of this concert event
     *
     * @return 3 hours (180 minutes)
     */
    public int getDuration() {
         return 180;
    }
}