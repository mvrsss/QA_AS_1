
public class Account {
    private String clientId;
    private int balance;
    private Type type;

    private int interestRate;

    private int overdraftLimit;

    public enum Type {
        CHECKING,
        SAVINGS
    }

    public Account(String clientId, int balance, Type type) {
        this.clientId = clientId;
        this.balance = balance;
        this.type = type;
        this.overdraftLimit = 0; // Initialize overdraft limit to 0 by default
    }

    // Getters
    public String getClientId() {
        return clientId;
    }

    public int getBalance() {
        return balance;
    }

    public Type getType() {
        return type;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int rate) {
        this.interestRate = rate;
    }

    // Method to credit account
    public void credit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }

    // Method to debit account
    public void debit(int amount) {
        if (balance >= amount) {
            // Sufficient balance, perform debit
            balance -= amount;
        } else if (overdraftLimit >= (amount - balance)) {
            // Insufficient balance, but within overdraft limit
            balance = - (amount - balance);
        } else {
            // Insufficient balance and overdraft limit reached
            throw new IllegalArgumentException("Insufficient funds and overdraft limit reached");
        }
    }

    public void debitMutant1(int amount) {
        if (balance < amount) { // Changed >= to <
            balance -= amount;
        } if (overdraftLimit >= (amount - balance)) { // removed else if
            balance = - (amount - balance);
        } else {
            throw new IllegalArgumentException("Insufficient funds and overdraft limit reached");
        }
    }

    public void debitMutant2(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } if (overdraftLimit < (amount - balance)) { // Changed >= to >
            balance = - (amount - balance);
        } else {
            throw new IllegalArgumentException("Insufficient funds and overdraft limit reached");
        }
    }

    public void debitMutant3(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } if (overdraftLimit >= (amount - balance)) {
            balance = amount - balance; // Removed the negative sign
        } else {
            throw new IllegalArgumentException("Insufficient funds and overdraft limit reached");
        }
    }

    public void debitMutant4(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } if (overdraftLimit >= (amount - balance)) {
            balance = - (amount - balance);
        } else {
            // Removed the throw statement
            // throw new IllegalArgumentException("Insufficient funds and overdraft limit reached");
        }
    }


    // Method to apply interest
    public void applyInterest(double rate) {
        int interest = (int)(balance * (rate / 100));
        balance += interest;
    }

    // Method to enable overdraft protection
    public void enableOverdraftProtection(int limit) {
        this.overdraftLimit = limit;
    }
}
