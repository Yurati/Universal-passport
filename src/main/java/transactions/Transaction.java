package transactions;

import lombok.Getter;
import operations.Operation;
import utils.BlockUtils;
import utils.KeyUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Logger;

public class Transaction {
    private static final Logger LOGGER = Logger.getLogger("Transaction");
    @Getter
    private String transactionId;
    private final PublicKey sender;
    @Getter
    private final String passportId;
    private final Operation operation;
    private byte[] signature;

    private static long sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, String to, Operation operation) {
        this.sender = from;
        this.passportId = to;
        this.operation = operation;
        transactionId = calculateHash();
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = KeyUtils.getStringFromKey(sender) + passportId + operation;
        signature = KeyUtils.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = KeyUtils.getStringFromKey(sender) + passportId + operation;
        return KeyUtils.verifyECDSASig(sender, data, signature);
    }

    //TODO: how to process transaction?
    public void processTransaction() {
        if (!verifySignature()) {
            LOGGER.severe("Signature failed to verify!");
        }
        operation.execute();
    }

    private String calculateHash() {
        sequence++;
        return BlockUtils.applySha256(
                KeyUtils.getStringFromKey(sender) +
                        passportId +
                        operation + sequence
        );
    }

}
