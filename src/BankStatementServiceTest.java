import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankStatementServiceTest {

    BankStatementService service = new BankStatementService();

    // A - provide bank statement
    // B - display balance

    @Test
    public void testCase1() {
        // Case 1: True, True, True, True, True -> A, B
        assertTrue(service.retrieveStatement(true, true, true, true, true).contains("StatementProvided"));
        assertTrue(service.retrieveStatement(true, true, true, true, true).contains("BalanceDisplayed"));
    }

    @Test
    public void testCase2() {
        // Case 2: True, True, True, False, True -> A
        assertTrue(service.retrieveStatement(true, true, true, false, true).contains("StatementProvided"));
    }

    @Test
    public void testCase3() {
        // Case 3: True, True, False, False, False -> E
        assertTrue(service.retrieveStatement(true, true, false, false, false).contains("InvalidDateRangeError"));
    }

    @Test
    public void testCase4() {
        // Case 4: True, False, True, False, True -> E
        assertTrue(service.retrieveStatement(true, false, true, false, true).contains("AccountInactiveError"));
    }

    @Test
    public void testCase5() {
        // Case 5: False, False, False, False, False -> E
        assertTrue(service.retrieveStatement(false, false, false, false, false).contains("AccountInactiveError"));
    }
}
