package com.qa.as1;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceTest {

    @Test
    public void testClientAuthentication() {
        // Initialize AuthenticationService with a user
        AuthenticationService authService = new AuthenticationService();
        authService.register("user123", "password");

        // Attempt login with correct credentials
        boolean loginResult = authService.login("user123", "password");

        // Assert the login is successful
        assertTrue(loginResult);
    }
}
