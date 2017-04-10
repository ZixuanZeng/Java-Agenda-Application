public class EventNotFoundException extends Exception {
             
    private int id;
    private String eventType;

    public EventNotFoundException(int id) {
         this(id, "EVENT");
    }

    public EventNotFoundException(int id, String eventType) {
         this.id = id;
         this.eventType = eventType;
    }
   
    public String getMessage() {
       if (eventType.equals("EVENT"))
           return eventType + " NOT FOUND:"+id;
       else
           return eventType + " NOT FOUND: "+id;
    }  
}