import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private String name;

    private Map<String, Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
    }

    // Method to create client
    public Client createClient(String name, String surname, String username, LocalDateTime dateOfBirth, String address) {
        return new Client(name, surname, username, dateOfBirth, address);
    }

    public Account createAccount(String clientId, int initialBalance, Account.Type type) {
        // Create a new account
        Account account = new Account(clientId, initialBalance, type);
        // Associate the account with the client
        accounts.put(account.getClientId(), account);
        return account;
    }

    // Method to remove account
    public boolean removeAccount(String accountNumber) {
        accounts.remove(accountNumber);
        return true;
    }

    // Method to get account by number
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Method to close account
    public boolean closeAccount(String accountNumber) {
        // Retrieve the account
        Account account = accounts.get(accountNumber);
        if (account != null) {
            if (account.getBalance() != 0) {
                throw new IllegalArgumentException("Account balance is not zero, cannot close account.");
            }
            // Remove the account from the bank's accounts map
            accounts.remove(accountNumber);
            return true;
        }
        return false;
    }


    // Method to credit an account with a specified amount
    public void credit(String accountNumber, int amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.credit(amount);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    // Method to debit an account with a specified amount
    public void debit(String accountNumber, int amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.debit(amount);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    // Method to apply interest
    public void applyInterest(String clientId) {
        Account account = getAccountByClientId(clientId);
        if (account != null && account.getType() == Account.Type.SAVINGS) {
            account.applyInterest(account.getInterestRate()); // Apply interest to the savings account
        } else {
            throw new IllegalArgumentException("Savings account not found for client");
        }
    }

    // Method to retrieve the account associated with the client
    private Account getAccountByClientId(String clientId) {
        for (Account account : accounts.values()) {
            if (account.getClientId().equals(clientId)) {
                return account;
            }
        }
        return null; // Account not found
    }

    // Method to get account balance
    public int getAccountBalance(String accountNumber) {
        return accounts.get(accountNumber).getBalance();
    }

    // Method to create savings account
    public Account createSavingsAccount(String clientId, int interestRate) {
        // Create a new savings account
        Account account = new Account(clientId, 0, Account.Type.SAVINGS);
        account.setInterestRate(interestRate); // Set the interest rate for the account
        // Store the account in the bank's accounts map
        accounts.put(account.getClientId(), account);
        return account;
    }
}
