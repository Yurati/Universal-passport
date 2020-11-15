package chain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import testutils.ParentTest;
import transactions.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTest extends ParentTest {
    private static final int DIFFICULTY = 3;
    private static final String PASSPORT_ID = "1";
    private static Block block;
    private static Transaction expectedTransaction;

    @BeforeAll
    public static void setUpBlockTest() {
        Transaction testTransaction = dataProvider.getDefaultTransaction();
        expectedTransaction = dataProvider.getDefaultTransaction();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(testTransaction);
        transactionList.add(expectedTransaction);
        block = new Block(null, transactionList);
    }

    @Test
    void mineBlockTest() {
        block.mineBlock(DIFFICULTY);
        assertFalse(block.getHash().isEmpty(), "Hash should be generated!");
    }

    @Test
    void getLastTransactionsForPassportTest() {
        Optional<Transaction> lastTransaction = block.getLastTransactionForPassport(PASSPORT_ID);
        assertTrue(lastTransaction.isPresent(), "There should be transaction for passport");
        assertEquals(expectedTransaction.getTimestamp(), lastTransaction.get().getTimestamp(),
                "Last transaction doesn't have expected timestamp");
    }
}