package testutils;

import blockchain.chain.Blockchain;
import blockchain.exceptions.TransactionsSizeException;
import blockchain.transactions.Transaction;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentTest {
    protected static Blockchain blockchain;
    protected static DataProvider dataProvider;

    @BeforeAll
    public static void setUpParentTest() {
        blockchain = new Blockchain();
        dataProvider = new DataProvider();
        generateDataForBlockchain();
    }

    private static void generateDataForBlockchain() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(dataProvider.getDefaultTransaction());
        transactionList.add(dataProvider.getDefaultTransaction());

        Transaction transactionForSecondBlock = new Transaction(dataProvider.getPublicKey(),
                dataProvider.getPassportWithVisaData());

        transactionList.add(transactionForSecondBlock);
        try {
            blockchain.mineBlock(transactionList);
            blockchain.mineBlock(Collections.singletonList(transactionForSecondBlock));
        } catch (TransactionsSizeException e) {
            e.printStackTrace();
        }
    }
}
