package transactions;

import chain.Blockchain;
import lombok.Getter;
import operations.Operation;
import utils.BlockUtils;
import utils.KeyUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Transaction {
    private static final Logger LOGGER = Logger.getLogger("Transaction");
    @Getter
    private String transactionId;
    private final PublicKey sender;
    private final PublicKey recipient;
    private final Operation operation;
    private byte[] signature;

    private final ArrayList<TransactionInput> inputs;
    private ArrayList<TransactionOutput> outputs = new ArrayList<>();

    private static long sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, PublicKey to, Operation operation, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.operation = operation;
        this.inputs = inputs;
    }

    private String calculateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return BlockUtils.applySha256(
                KeyUtils.getStringFromKey(sender) +
                        KeyUtils.getStringFromKey(recipient) +
                        operation + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = KeyUtils.getStringFromKey(sender) + KeyUtils.getStringFromKey(recipient) + operation;
        signature = KeyUtils.applyECDSASig(privateKey,data);
    }

    public boolean verifySignature() {
        String data = KeyUtils.getStringFromKey(sender) + KeyUtils.getStringFromKey(recipient) + operation;
        return KeyUtils.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction() {

        if(!verifySignature()) {
            LOGGER.severe("Signature failed to verify!");
            return false;
        }

        //gather transaction inputs (Make sure they are unspent):
        for(TransactionInput transactionInput: inputs) {
            transactionInput.UTXO = Blockchain.UTXOs.get(transactionInput.id);
        }

        //generate transaction outputs:
        transactionId = calculateHash();
        outputs.add(new TransactionOutput( this.recipient, operation, transactionId)); //send value to recipient
        outputs.add(new TransactionOutput( this.sender, operation, transactionId)); //send the left over 'change' back to sender

        //add outputs to Unspent list
        for(TransactionOutput transactionOutput : outputs) {
            Blockchain.UTXOs.put(transactionOutput.id , transactionOutput);
        }

        //remove transaction inputs from UTXO lists as spent:
        for(TransactionInput transactionInput : inputs) {
            if(transactionInput.UTXO == null) continue;
            Blockchain.UTXOs.remove(transactionInput.UTXO.id);
        }
        LOGGER.info("Transaction successful!");
        return true;
    }

}
