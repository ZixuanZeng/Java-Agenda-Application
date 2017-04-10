public class Birthday extends Anniversary {

    public Birthday(String description, User user, int number, String date) {
        super(description, user, number, date);
    }

    @Override
    public String getEventType() {
         return "BIRTHDAY";
    }

    public Event generateNext() {

        return new Birthday(getDescription(), getUser(), getId(), generateNextDate());
    }
}                       