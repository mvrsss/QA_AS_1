package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class AccountCreationIntegrationTest {

    @Test
    public void testAccountCreation() {
        AuthenticationService authService = mock(AuthenticationService.class);
        Bank bank = new Bank("MyBank");
        
        // Assuming a method to set AuthenticationService in Bank or during client creation
        Client client = bank.createClient("John", "Doe", "johndoe", LocalDateTime.now(), "123 Main Street");
        
        // Simulate successful registration
        doNothing().when(authService).register(anyString(), anyString());
        
        // Create an account
        Account account = bank.createAccount(client.getId(), 1000, Account.Type.CHECKING);
        
        assertNotNull(account);
        assertEquals(1000, account.getBalance());
    }
}
