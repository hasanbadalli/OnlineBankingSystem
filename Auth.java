import java.util.HashMap;
import java.util.Map;

public class Auth {
    private String username, password;
    private CustomerType customerType;
    private boolean loginStatus = false;
    public Register register;
    public static Map<String, String> users = new HashMap<>();

    public Auth(String username, String password, CustomerType customerType, Register register) {
        this.username = username;
        this.password = password;
        this.customerType = customerType;
        this.register = register;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void registerCheck(){
        if(register == Register.SIGNUP){
            users.put(username, password);
            System.out.println("SIGN UP SUCCESSFULLY...");
        } else if (register == Register.LOGIN) {
            try{
                if(users.get(username).equals(password)){
                    System.out.println("LOG IN SUCCESSFULLY...");
                    loginStatus = true;
                }
                else{
                    System.out.println("Your Password is not Correct");
                }
            }catch (NullPointerException e){
                System.out.println("Can not find this user");
            }
        }
    }
    static public void getUsers(){
        users.forEach((key, value)-> System.out.println("Username: " + key + " Password: " + value));
    }
}
