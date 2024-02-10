import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class BankingSystemTest {

    @Test
    void testClientCreation() {
        LocalDateTime dob = LocalDateTime.of(1990, Month.JANUARY, 1, 0, 0);
        Client client = new Client("John", "Doe", "johndoe", dob, "123 Main St");
        assertAll(
                () -> assertEquals("John", client.getName()),
                () -> assertEquals("Doe", client.getSurname()),
                () -> assertEquals("johndoe", client.getUsername()),
                () -> assertEquals(dob, client.getDateOfBirth()),
                () -> assertEquals("123 Main St", client.getAddress()),
                () -> assertNotNull(client.getId())
        );
    }

    @Test
    void testAccountCreationAndGetters() {
        Account account = new Account("123456", 1000, Account.Type.CHECKING);
        assertAll(
                () -> assertEquals("123456", account.getClientId()),
                () -> assertEquals(1000, account.getBalance()),
                () -> assertEquals(Account.Type.CHECKING, account.getType())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, -50})
    void testCreditOperation(int amount) {
        Account account = new Account("123456", 1000, Account.Type.CHECKING);
        if (amount > 0) {
            account.credit(amount);
            assertEquals(1000 + amount, account.getBalance());
        } else {
            assertThrows(IllegalArgumentException.class, () -> account.credit(amount));
        }
    }

    //didnt get what this test checks
    @ParameterizedTest
    @CsvSource({"200,800", "500,500", "1500, 'Insufficient funds'"})
    void testDebitOperation(int debitAmount, String expected) {
        Account account = new Account("123456", 1000, Account.Type.CHECKING);
        if (!"Insufficient funds".equals(expected)) {
            account.debit(debitAmount);
            assertEquals(Integer.parseInt(expected), account.getBalance());
        } else {
            assertThrows(IllegalArgumentException.class, () -> account.debit(debitAmount));
        }
    }

    @Test
    void testInterestApplication() {
        Account account = new Account("78910", 1000, Account.Type.SAVINGS);
        Savings savings = new Savings(account, 5); // 5% interest
        int expectedInterest = (int)(1000 * 0.05);
        savings.applyInterest();
        assertEquals(1000 + expectedInterest, account.getBalance());
    }

    @Test
    void testCreateAccount() {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.now(), "456 Elm St");
        Account account = bank.createAccount(client.getId(), 0,  Account.Type.CHECKING);
        assertNotNull(account);
        assertEquals(Account.Type.CHECKING, account.getType());
    }

    @Test
    void testRemoveAccount() {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.now(), "456 Elm St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.CHECKING);
        assertTrue(bank.removeAccount(account.getClientId()));
        assertNull(bank.getAccount(account.getClientId()));
    }

    @ParameterizedTest
    @CsvSource({
            "credit,100,1000,1100",
            "debit,100,1000,900"
    })
    void testCreditAndDebitOperations(String operation, int amount, int currentBalance, int expectedBalance) {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.now(), "456 Elm St");
        Account account = bank.createAccount(client.getId(), currentBalance, Account.Type.CHECKING);

        if ("credit".equals(operation)) {
            bank.credit(account.getClientId(), amount);
        } else {
            bank.debit(account.getClientId(), amount);
        }

        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    void testClientAccountLinking() {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.now(), "456 Elm St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.CHECKING);
        assertEquals(client.getId(), account.getClientId());
    }

    @Test
    void testInsufficientFundsScenario() {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Jane", "Doe", "janedoe", LocalDateTime.now(), "456 Elm St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.CHECKING);
        Exception exception = assertThrows(RuntimeException.class, () -> bank.debit(account.getClientId(), 1500));
        assertEquals("Insufficient funds and overdraft limit reached", exception.getMessage());
    }

    @Test
    void testUpdateClientAddress() {
        Bank bank = new Bank("Test bank");
        Client client = bank.createClient("Sam", "Smith", "samsmith", LocalDateTime.of(1988, Month.APRIL, 22, 0, 0), "123 Pine St");
        String newAddress = "456 Oak St";
        client.updateAddress(newAddress);
        assertEquals(newAddress, client.getAddress());
    }

    @Test
    void testConcurrentDeposits() throws InterruptedException {
        final Account account = new Account("111222", 1000, Account.Type.CHECKING);
        int numberOfThreads = 10;
        int amountPerThread = 100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> account.credit(amountPerThread));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        assertEquals(2000, account.getBalance());
    }

    @Test
    void testDebitWithOverdraft() {
        Account account = new Account("333444", 100, Account.Type.CHECKING);
        account.enableOverdraftProtection(200); // Enable overdraft up to 200
        account.debit(250); // Attempt to debit more than balance but within overdraft limit
        assertEquals(-150, account.getBalance()); // Overdraft allows balance to go negative
    }

    @Test
    void testCloseAccountWithZeroBalance() {
        Bank bank = new Bank("Test bank");
        Client client = bank.createClient("Lily", "Evans", "lilyevans", LocalDateTime.of(1990, Month.MAY, 15, 0, 0), "789 Birch St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.SAVINGS);
        assertTrue(bank.closeAccount(account.getClientId()));
    }

    @Test
    void testCloseAccountNonZeroBalanceFails() {
        Bank bank = new Bank("Test bank");
        Client client = bank.createClient("James", "Potter", "jamespotter", LocalDateTime.of(1989, Month.JUNE, 21, 0, 0), "101 Maple St");
        Account account = bank.createAccount(client.getId(), 100, Account.Type.SAVINGS);
        account.credit(100); // Account now has a balance
        assertThrows(IllegalArgumentException.class, () -> bank.closeAccount(account.getClientId()));
    }

    @Test
    void testConcurrentAccountCredits() throws InterruptedException {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Bob", "Brown", "bobbrown", LocalDateTime.of(1985, 8, 15, 0, 0), "789 Pine St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.CHECKING);
        int initialBalance = account.getBalance();
        int numberOfThreads = 10;
        int amountPerThread = 100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> bank.credit(account.getClientId(), amountPerThread));
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(initialBalance + numberOfThreads * amountPerThread, account.getBalance());
    }

    @Test
    void testClosingAccountWithPositiveBalance() {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Cindy", "Green", "cindygreen", LocalDateTime.now(), "456 Willow St");
        Account account = bank.createAccount(client.getId(), 0, Account.Type.SAVINGS);
        bank.credit(account.getClientId(), 1000);
        assertThrows(IllegalArgumentException.class, () -> bank.closeAccount(account.getClientId()));
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 5, 1050",
            "2000, 3, 2060",
            "0, 5, 0"
    })
    void testInterestApplication(int initialBalance, int interestRate, int expectedBalance) {
        Bank bank = new Bank("Test Bank");
        Client client = bank.createClient("Derek", "Blue", "derekblue", LocalDateTime.now(), "123 Maple St");
        Account account = bank.createSavingsAccount(client.getId(), interestRate);
        bank.credit(account.getClientId(), initialBalance);
        bank.applyInterest(account.getClientId());
        assertEquals(expectedBalance, bank.getAccountBalance(account.getClientId()));
    }

    @Test
    void testClientLoginSuccess() {
        AuthenticationService authService = new AuthenticationService();
        Client client = new Client("Bob", "Builder", "bobbuild", LocalDateTime.of(1975, 5, 15, 0, 0), "789 Construct Ln");
        authService.register(client.getUsername(), "safePassword123"); // Assuming a registration step exists
        assertTrue(authService.login(client.getUsername(), "safePassword123"));
    }

    @Test
    void testClientLoginFailure() {
        AuthenticationService authService = new AuthenticationService();
        Client client = new Client("Eve", "Dropper", "evedrop", LocalDateTime.of(1985, 10, 30, 0, 0), "321 Sneak St");
        authService.register(client.getUsername(), "securePass456"); // Assuming a registration step exists
        assertFalse(authService.login(client.getUsername(), "wrongPassword"));
    }



















































}