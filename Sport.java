public class Sport extends EntertaimentEvent {

    public Sport(String description, int quota, int number, String date, String time) {
        super(description, quota, number, date, time);          
    }

    @Override
    public String getEventType() {
        return "SPORT";
    }

    /**
     * Retuns duration of this sport event
     *
     * @return 4 hours (240 minutes)
     */
    public int getDuration() {
         return 240;
    }
}