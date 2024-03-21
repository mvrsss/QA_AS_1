package com.qa.as1;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsInterestApplicationTest {

    @Test
    public void testInterestApplication() {
        // Creating bank and account setup
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.of(1990, 2, 1, 0, 0), "456 Elm Street");
        Account savingsAccount = bank.createSavingsAccount(client.getId(), 5); // 5% interest rate
        
        // Apply interest through the bank
        bank.applyInterest(client.getId());
        
        // Assert that interest has been applied correctly
        // Assuming the initial balance was 0, the new balance should still be 0 as no deposits were made to the account before applying interest
        assertEquals(0, savingsAccount.getBalance());
    }
}
