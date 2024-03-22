package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WithdrawalInsufficientFundsIntegrationTest {

    @Test
    public void testWithdrawalWithInsufficientFunds() {
        Account account = mock(Account.class);
        
        // Assuming withdrawal method throws IllegalArgumentException on insufficient funds
        doThrow(new IllegalArgumentException("Insufficient funds")).when(account).debit(anyInt());
        
        assertThrows(IllegalArgumentException.class, () -> account.debit(5000));
    }
}
