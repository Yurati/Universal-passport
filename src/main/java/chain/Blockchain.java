package chain;

import transactions.TransactionOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Blockchain {
    private static final Logger LOGGER = Logger.getLogger("Blockchain");
    private List<Block> blockchain;
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

    public Blockchain() {
        blockchain = new ArrayList<>();
    }

    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        LOGGER.info("Validating blockchain.");
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                LOGGER.warning("Chain is not valid!");
                return false;
            }
        }
        LOGGER.info("Chain is valid.");
        return true;
    }
}
