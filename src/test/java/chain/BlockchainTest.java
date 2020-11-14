package chain;

import org.junit.jupiter.api.Test;
import testutils.ParentTest;
import transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BlockchainTest extends ParentTest {

    @Test
    void getPassportTest() {

    }

    @Test
    void mineBlockTest() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(dataProvider.getDefaultTransaction());
        int blockchainOldSize = blockchain.getBlockchain().size();
        blockchain.mineBlock(transactionList);
        assertEquals(blockchainOldSize + 1, blockchain.getBlockchain().size(),
                "Blockchain size should increase");
    }
}