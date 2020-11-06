package transactions;

import operations.Operation;
import utils.BlockUtils;
import utils.KeyUtils;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey recipient;
    public Operation operation;
    public String parentTransactionId; //the id of the transaction this output was created in

    public TransactionOutput(PublicKey recipient, Operation operation, String parentTransactionId) {
        this.recipient = recipient;
        this.operation = operation;
        this.parentTransactionId = parentTransactionId;
        this.id = BlockUtils.applySha256(KeyUtils.getStringFromKey(recipient)+ operation +parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == recipient);
    }

}
