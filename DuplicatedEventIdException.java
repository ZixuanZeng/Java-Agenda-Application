public class DuplicatedEventIdException extends Exception {

    private int id;

    public DuplicatedEventIdException(int id) {
         this.id = id;
    }
   
    public String getMessage() {
       return "DUPLICATED ID: "+id+" ALREADY EXIST";
    }
}