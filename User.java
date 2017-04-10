public class User
{
    private int number;
    private String name;  

    /**
     * Creates user obkject instance
     *
     * @param number user id, should be unique
     * @name user full name
     */
    public User(int number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * Compares two users
     *
     * @param otherUsers other user to compare
     * @return true, if user are equals
     */     
    public boolean equals(User otherUsers) {
         return number==otherUsers.number;
    }

    /**
     * Returns id of user
     *
     * @return user id
     */
    public int getId() {
         return number;
    }

    /**
     * Returns name of user
     *
     * @return user name
     */
    public String getName() {
         return name;
    }
}