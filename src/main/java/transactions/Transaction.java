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
    private final PublicKey recipient;
    private final Operation operation;
    private byte[] signature;

    private static long sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, PublicKey to, Operation operation) {
        this.sender = from;
        this.recipient = to;
        this.operation = operation;
        transactionId = calculateHash();
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = KeyUtils.getStringFromKey(sender) + KeyUtils.getStringFromKey(recipient) + operation;
        signature = KeyUtils.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = KeyUtils.getStringFromKey(sender) + KeyUtils.getStringFromKey(recipient) + operation;
        return KeyUtils.verifyECDSASig(sender, data, signature);
    }

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
                        KeyUtils.getStringFromKey(recipient) +
                        operation + sequence
        );
    }

}
