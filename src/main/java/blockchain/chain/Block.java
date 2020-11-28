package blockchain.chain;

import blockchain.transactions.Transaction;
import blockchain.utils.BlockUtils;
import lombok.Getter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Getter
public class Block implements Serializable {
    private static final Logger LOGGER = Logger.getLogger("Block");
    private String hash;
    private final String previousHash;
    public List<Transaction> transactions;
    private final long timestamp;
    private int nonce;

    public Block(String previousHash, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.transactions = transactions;
        nonce = 0;
    }

    public void mineBlock(int prefix) {
        LOGGER.info("Calculating hash for block");
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        do {
            nonce++;
            hash = calculateBlockHash();
        } while (!hash.substring(0, prefix).equals(prefixString));
        LOGGER.info(String.format("Block with hash %s mined!", hash));
    }

    public Optional<Transaction> getLastTransactionForPassport(String id) {
        return transactions
                .stream()
                .filter(transaction -> transaction.getPassport().getId().equals(id))
                .collect(Collectors.toList())
                .stream()
                .max(Comparator.comparing(Transaction::getTimestamp));
    }

    private String calculateBlockHash() {
        String dataToHash = previousHash
                + timestamp
                + nonce
                + BlockUtils.getMerkleRoot(transactions);
        return BlockUtils.applySha256(dataToHash);
    }
}
