public class QuotaFullException extends RuntimeException {

    private int id;

    public QuotaFullException(int id) {
         this.id = id;
    }
   
    public String getMessage() {
       return "QUOTA FULL: "+id;
    }  
}