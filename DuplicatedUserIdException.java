public class DuplicatedUserIdException extends Exception {

    private int id;

    public DuplicatedUserIdException(int id) {
         this.id = id;
    }
   
    public String getMessage() {
       return "DUPLICATED USER ID: "+id+" ALREADY EXIST";
    }
}