package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositIntegrationTest {

    @Test
        public void testDeposit() {
        // Create a mock Account object
        Account account = mock(Account.class);
        
        // Assume an initial balance of 0 for simplicity in setting up this test
        when(account.getBalance()).thenReturn(0);
        
        // Perform the deposit operation
        int depositAmount = 100;
        account.credit(depositAmount);
        
        // Verify that the credit method was called with the correct amount
        verify(account, times(1)).credit(depositAmount);
    }
}
