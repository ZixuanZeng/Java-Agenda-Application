import java.util.LinkedList;

public abstract class EntertaimentEvent extends QuotedEvent implements LongEvent {

    private String description;

    public EntertaimentEvent(String description, int quota, int number, String date, String time) {
        super(quota, number, date, time);
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

    @Override
    public boolean isEntertaimentEvent() {
        return true;
    }

    public String toString(int number) {
        return description + " ON " + getDate() + " " + getTime();
    }
}
