package testutils;

import chain.Blockchain;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import transactions.Transaction;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParentTest {
    protected static Blockchain blockchain;
    protected static DataProvider dataProvider;

    @BeforeAll
    public static void setUpParentTest() {
        //TODO: add to config
        Security.addProvider(new BouncyCastleProvider());
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
        blockchain.mineBlock(transactionList);
        blockchain.mineBlock(Arrays.asList(transactionForSecondBlock));
    }
}
