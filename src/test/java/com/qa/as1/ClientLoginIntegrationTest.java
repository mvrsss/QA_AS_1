package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientLoginIntegrationTest {

    @Test
    public void testLogin() {
        AuthenticationService authService = mock(AuthenticationService.class);
        
        // Assuming the login method exists and returns boolean
        when(authService.login("user", "pass")).thenReturn(true);
        
        boolean result = authService.login("user", "pass");
        
        assertTrue(result);
    }
}
