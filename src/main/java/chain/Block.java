package chain;

import lombok.Getter;
import transactions.Transaction;
import utils.BlockUtils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Getter
public class Block {
    private static final Logger LOGGER = Logger.getLogger("Block");
    private String hash;
    private final String previousHash;
    public List<Transaction> transactions;
    private final long timeStamp;
    private int nonce;

    public Block(String previousHash, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.transactions = transactions;
    }

    public void mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        LOGGER.info(String.format("Block with hash %s mined!", hash));
    }

    public List<Transaction> getTransactionsForPassport(String id) {
        return transactions
                .stream()
                .filter(transaction -> transaction.getTransactionId().equals(id))
                .collect(Collectors.toList());
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
