package transactions;

import org.junit.jupiter.api.Test;
import testutils.ParentTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest extends ParentTest {

    @Test
    public void verifySignatureTest() {
        Transaction transaction = dataProvider.getDefaultTransaction();

        transaction.generateSignature(dataProvider.getPrivateKey());
        assertTrue(transaction.verifySignature(), "Signature validation failed");
    }
}