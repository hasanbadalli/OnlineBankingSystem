import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private static int nextAccountNumber = 1;
    private int accountNumber;
    private String nameSurname;
    private double balance;
    private AccountType accountType;
    private final static List<String> transactionHistory = new ArrayList<>();

    public BankAccount(String nameSurname, double initialBalance, AccountType accountType) {
        this.accountNumber = nextAccountNumber++;
        this.nameSurname = nameSurname;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void deposit(double amount) {
        balance += amount;

    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        balance -= amount;

    }

    public static void addTransaction(String transactionDetail) {
        transactionHistory.add(transactionDetail);
    }

    public static void writeTransaction(){
        for (int i = 0; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
    }


    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + nameSurname + ", Balance: " + balance + ", Account Type: " + accountType;
    }
}
