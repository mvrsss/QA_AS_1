package com.qa.as1;
public class Savings {
    private Account account;
    private double interestRate;


    public Savings(Account account, double interestRate) {
        this.account = account;
        this.interestRate = interestRate;
    }

    // Method to apply interest to the associated account
    public void applyInterest() {
        // Retrieve account from database or any other data source based on accountId
        if (account != null) {
            account.applyInterest(interestRate);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }
}
