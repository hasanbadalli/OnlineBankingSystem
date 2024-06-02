import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Customer extends Auth {
    private int customerID, phone;
    private String nameSurname, address, email;
    private static Map<Integer, BankAccount> bankAccounts = new HashMap<>();
    public Customer(String username, String password, CustomerType customerType, Register register) {
        super(username, password, customerType, register);
    }

    public Customer(String username, String password, CustomerType customerType, Register register, String nameSurname,int customerID) {
        super(username, password, customerType, register);
        this.nameSurname = nameSurname;
        this.customerID =  customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void createBankAcount(){
        BankAccount bankAccount = new BankAccount(nameSurname, 1000, AccountType.SAVING);
        bankAccounts.put(customerID, bankAccount);
    }
    public void printBankAccounts() {
        bankAccounts.forEach((id, account) -> System.out.println("Customer ID: " + id + ", Account: " + account));
    }
    public void getBalance(int newCustomerID){
        BankAccount account = bankAccounts.get(newCustomerID);

        System.out.println(bankAccounts.get(newCustomerID));
        if(account != null){
            System.out.println("Your Balance is " + account.getBalance());
        }else{
            System.out.println("Account is not found for this CardId " + customerID);
        }
    }

    public void transfer(int fromCustomerID,int toCustomerID, double amount){
        BankAccount fromCustomer = bankAccounts.get(fromCustomerID);
        BankAccount toCustomer = bankAccounts.get(toCustomerID);
        if(fromCustomer != null && toCustomer != null){
            try {
                fromCustomer.withdraw(amount);
                toCustomer.deposit(amount);
                System.out.println("Transfered amount from CustomerID: " + fromCustomerID + " to CustomerID: " + toCustomerID);
                System.out.println("Tranfer detail: ");
                String censoredName = censorname(toCustomer.getNameSurname());
                System.out.println("Transfer: " + TransactionStatus.SUCCESS);
                System.out.println("Transfered to " + censoredName);

                LocalDateTime now = LocalDateTime.now();


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);

                BankAccount.addTransaction("Transfer between" + fromCustomer + toCustomer +  "(" + formattedDate + "): " + amount);
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("Please check CardID");
        }
    }
    private String censorname(String nameSurname){
        String[] parts = nameSurname.split(" ");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            String firstLetter = part.substring(0, 1);  // Get the first character
            String censoredPart = firstLetter + part.substring(1).replaceAll(".", "*");
            result.append(censoredPart).append(" ");
        }

        return result.toString().trim();  // Remove the trailing space
    }

    public void addBalance(int newCustomerID, double addedBalance){
        BankAccount customer = bankAccounts.get(newCustomerID);
        if(customer != null){
            customer.deposit(addedBalance);
            System.out.println("Balance added status: " + TransactionStatus.SUCCESS);
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = now.format(formatter);

            BankAccount.addTransaction("Deposit" + "(" + formattedDate + "): " + addedBalance);
        }else{
            System.out.println("Cannnot find this card");
        }
    }

    public void withDrawBalance(int newCustomerID, double withDrawedBalance){
        BankAccount customer = bankAccounts.get(newCustomerID);
        if(customer != null){
            try {
                customer.withdraw(withDrawedBalance);
                System.out.println("Balance added status: " + TransactionStatus.SUCCESS);
                LocalDateTime now = LocalDateTime.now();


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = now.format(formatter);

                BankAccount.addTransaction("Withdraw" + "(" + formattedDate + "): " + withDrawedBalance);
            } catch (InsufficientFundsException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Cannnot find this card");
        }
    }

}
