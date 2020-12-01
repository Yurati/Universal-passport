package blockchain.chain;

import blockchain.data.Passport;
import blockchain.exceptions.TransactionsSizeException;
import blockchain.transactions.Transaction;
import org.junit.jupiter.api.Test;
import testutils.ParentTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BlockchainTest extends ParentTest {

    @Test
    void getPassportTest() {
        Passport passport = blockchain.getPassport("1");
        assertNotNull(passport, "Passport should be returned");
        assertEquals(dataProvider.getPassportWithVisaData(), passport, "Last passport modification " +
                "was not returned");
    }

    @Test
    void mineBlockTest() throws TransactionsSizeException {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(dataProvider.getDefaultTransaction());
        int blockchainOldSize = blockchain.getBlockchain().size();
        blockchain.mineBlock(transactionList);
        assertEquals(blockchainOldSize + 1, blockchain.getBlockchain().size(),
                "Blockchain size should increase");
    }

    @Test
    void isChainValid() {
        assertTrue(blockchain.isChainValid(), "Blockchain should be valid");
    }
}