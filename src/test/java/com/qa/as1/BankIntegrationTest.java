package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;


public class BankIntegrationTest {

    @Test
    public void testClientAccountCreationAndRegistration() {
        // Mocking AuthenticationService
        AuthenticationService authService = mock(AuthenticationService.class);
        
        // Bank setup
        Bank bank = new Bank("Test Bank");
        
        // Client creation
        Client client = bank.createClient("John", "Doe", "johndoe", LocalDateTime.of(1990, 1, 1, 0, 0), "123 Main St");
        
        // Register client in the AuthenticationService
        authService.register(client.getUsername(), "password123");
        
        // Verify registration was called with correct username and password
        verify(authService, times(1)).register(eq("johndoe"), eq("password123"));
        
        // Create an account for the client
        Account account = bank.createAccount(client.getId(), 1000, Account.Type.SAVINGS);
        
        // Assertions to ensure account is created successfully
        assertNotNull(account);
        assertEquals(1000, account.getBalance());
        assertEquals(Account.Type.SAVINGS, account.getType());
    }
    
    // Include imports and necessary setup/mock configurations
}
