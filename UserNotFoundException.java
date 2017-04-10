public class UserNotFoundException extends Exception {

    private int id;

    public UserNotFoundException(int id) {
         this.id = id;
    }
   
    public String getMessage() {
       return "USER NOT FOUND: "+id;
    }  
}