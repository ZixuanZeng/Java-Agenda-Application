public class Theater extends EntertaimentEvent {

    public Theater(String description, int quota, int number, String date, String time) {
        super(description, quota, number, date, time);          
    }

    @Override
    public String getEventType() {
        return "THEATER";
    }

    /**
     * Retuns duration of this theater event
     *
     * @return 2 hours (120 minutes)
     */
    public int getDuration() {
         return 120;
    }
}