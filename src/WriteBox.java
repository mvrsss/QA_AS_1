import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount account;

    @Before
    public void setUp() {
        // Initialize the BankAccount object with a known state
        account = new BankAccount(1000.0); // Initial balance is 1000
    }

    @Test
    public void testDeposit() {
        String result = account.processTransaction("deposit", 500.0);
        assertEquals("Deposit successful", result);
        assertEquals(1500.0, account.getBalance(), 0.001); // Check if the balance is updated correctly
    }

    @Test
    public void testWithdrawalWithSufficientBalance() {
        String result = account.processTransaction("withdraw", 500.0);
        assertEquals("Withdrawal successful", result);
        assertEquals(500.0, account.getBalance(), 0.001); // Check if the balance is updated correctly
    }

    @Test
    public void testWithdrawalWithInsufficientBalance() {
        String result = account.processTransaction("withdraw", 1500.0);
        assertEquals("Insufficient balance", result);
        assertEquals(1000.0, account.getBalance(), 0.001); // Ensure balance remains unchanged
    }

    @Test
    public void testBalanceInquiry() {
        String result = account.processTransaction("inquiry", 0);
        assertEquals("Your balance is 1000.0", result);
    }

    @Test
    public void testUnrecognizedTransactionType() {
        String result = account.processTransaction("unknown", 0);
        assertEquals("Unrecognized transaction type", result);
    }
}