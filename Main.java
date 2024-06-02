import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.println("""
                -------------------
                        ||
                Welcome to Our Bank
                        ||
                -------------------
                """);
        boolean run = true;
        while (run) {
            System.out.println("""
                    (Capital latter doesn't matter)
                    Signup --> Signup
                    Login --> Login
                    Exit --> Exit
                    """);
            Register register = Register.EMPTY;
            try{
                String choose = scan.next();
                register = Register.valueOf(choose.toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Please Write Correct");
            }

            switch (register) {
                case Register.SIGNUP -> signup();
                case Register.LOGIN -> login();
                case Register.EXIT -> run = false;
                default -> System.out.println("Invalid option. Please write correct");
            }

        }
    }

    public static void login() {
        System.out.println("Write your username");
        String username = scan.next();
        System.out.println("Write your password");
        String password = scan.next();
        Customer auth = new Customer(username, password, CustomerType.CUSTOMER, Register.LOGIN);
        auth.registerCheck();

        if(auth.isLoginStatus()){
            boolean run = true;
            while (run){
                System.out.printf("""
                    Welcome, %s
                    I am Asistant bot for you
                    1 -> See Balance
                    2 -> Transfer Money
                    3 -> Add Money to balance
                    4 -> With draw money
                    5 -> See Transaction History
                    """, username);
                int choose = -1;
                try {
                    choose = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please write one of the given numbers");
                    scan.nextLine();
                    continue;
                }

                switch (choose) {
                    case 1 -> {
                        System.out.println("Write your Card Id");
                        int cardId = scan.nextInt();
                        System.out.println("Your Balance: ");
                        auth.getBalance(cardId);
                    }
                    /*
                    case 2 -> auth.printBankAccounts();
                    case 3 -> Auth.getUsers();
                    */

                    case 2-> {
                        System.out.println("Please write your cardId");
                        int cardId = scan.nextInt();
                        System.out.println("Please write cardId which you want to transfer money");
                        int toCardId = scan.nextInt();
                        System.out.println("How much money do you want to transfer?");
                        double money = scan.nextInt();
                        auth.transfer(cardId, toCardId, money);
                    }

                    case 3->{
                        System.out.println("Please write your cardId");
                        int cardId = scan.nextInt();
                        System.out.println("How much money do you want to deposit?");
                        double money = scan.nextInt();
                        auth.addBalance(cardId, money);

                    }
                    case 4->{
                        System.out.println("Please write your cardId");
                        int cardId = scan.nextInt();
                        System.out.println("How much money do you want to withdraw?");
                        double money = scan.nextInt();
                        auth.withDrawBalance(cardId, money);
                    }
                    case 5->{
                        BankAccount.writeTransaction();
                    }
                    case 0 -> {
                      run = false;
                    }
                    default -> System.out.println("Invalid option. Please choose 1 or 2.");
                }
            }
        }
    }

    public static void signup() {
        System.out.println("Write your username");
        String username = scan.next();
        System.out.println("Write your password");
        String password = scan.next();
        System.out.println("Write your Name and Surname");
        String nameSurname = scan.next();
        int randomUserID = (int) (Math.random() * 100000) + 1;
        Customer auth = new Customer(username, password, CustomerType.CUSTOMER, Register.SIGNUP, nameSurname, randomUserID);
        auth.registerCheck();
        System.out.println("Your card number(Remember this): " + randomUserID);
        auth.createBankAcount();
    }
}