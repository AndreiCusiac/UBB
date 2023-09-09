package ro.ubbcluj.map.domain.MyValidators;

public class UserRepoValidator extends Exception {
    //ArrayList<User> users;

    /**
     * Constructor UserRepoValidator default
     */
    public UserRepoValidator() {
    }

    /**
     * Constructor UserRepoValidator cu mesaj
     * @param message
     */
    public UserRepoValidator(String message) {
        super(message);
    }
}
