// Abstract Account class to manage basic account information
abstract class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public abstract void displayAccountType();
}

// SavingsAccount class to manage savings account specific information
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    public void calculateInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest);
        System.out.println("Interest calculated and added: $" + interest);
    }

    @Override
    public void displayAccountType() {
        System.out.println("Savings Account");
    }
}

// CurrentAccount class to manage current account specific information
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, double initialBalance, double overdraftLimit) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= getBalance() + overdraftLimit) {
            super.deposit(-amount); // using deposit with negative amount to reduce balance
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Exceeded overdraft limit or invalid amount.");
        }
    }

    @Override
    public void displayAccountType() {
        System.out.println("Current Account");
    }
}

// Main class to demonstrate the functionality
public class bankingsystem {
    public static void main(String[] args) {
        // Create SavingsAccount
        SavingsAccount savings = new SavingsAccount("SA123", 1000, 5);
        savings.displayAccountType();
        savings.deposit(200);
        savings.withdraw(150);
        savings.calculateInterest();
        System.out.println("Savings Account Balance: $" + savings.getBalance());

        // Create CurrentAccount
        CurrentAccount current = new CurrentAccount("CA123", 500, 1000);
        current.displayAccountType();
        current.deposit(300);
        current.withdraw(1000);
        current.withdraw(900); // This should trigger overdraft handling
        System.out.println("Current Account Balance: $" + current.getBalance());
    }
}
