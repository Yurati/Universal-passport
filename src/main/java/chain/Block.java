package chain;

import lombok.Getter;
import transactions.Transaction;
import utils.BlockUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

@Getter
public class Block {
    private static final Logger LOGGER = Logger.getLogger("Block");
    private String hash;
    private final String previousHash;
    public ArrayList<Transaction> transactions;
    private final long timeStamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        transactions = new ArrayList<>();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        LOGGER.info(String.format("Block with hash %s mined!", hash));
        return hash;
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;
        transactions.add(transaction);
        LOGGER.info(String.format("Transaction %s successfully added to block", transaction.getTransactionId()));
        return true;
    }

    private String calculateBlockHash() {
        LOGGER.info("Calculating hash for block");
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + BlockUtils.getMerkleRoot(transactions);
        return BlockUtils.applySha256(dataToHash);
    }
}
