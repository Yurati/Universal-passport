package testutils;

import chain.Blockchain;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;

import java.security.Security;

public class ParentTest {
    protected static Blockchain blockchain;
    protected static DataProvider dataProvider;

    @BeforeAll
    public static void setUpParentTest() {
        //TODO: add to config
        Security.addProvider(new BouncyCastleProvider());
        blockchain = new Blockchain();
        dataProvider = new DataProvider();
    }
}
