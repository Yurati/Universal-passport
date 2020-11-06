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
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
    private final long timeStamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();;
        this.hash = calculateBlockHash();
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

    private String calculateBlockHash() {
        LOGGER.info("Calculating hash for block");
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + merkleRoot;
        return BlockUtils.applySha256(dataToHash);
    }

    public boolean addTransaction(Transaction transaction) {
        LOGGER.info(String.format("Adding transaction to block %s", hash));
        if(transaction == null) return false;
        if((!previousHash.equals("0"))) {
            if((!transaction.processTransaction())) {
                LOGGER.warning("Transaction failed to process");
                return false;
            }
        }
        transactions.add(transaction);
        LOGGER.info(String.format("Transaction successfully added to block %s", hash));
        return true;
    }
}
