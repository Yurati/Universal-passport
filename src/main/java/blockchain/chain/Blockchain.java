package blockchain.chain;

import blockchain.data.Passport;
import blockchain.transactions.Transaction;
import lombok.Getter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Blockchain {
    private static final Logger LOGGER = Logger.getLogger("Blockchain");
    private static final int DIFFICULTY = 3;
    @Getter
    private LinkedList<Block> blockchain;

    public Blockchain() {
        blockchain = new LinkedList<>();
    }

    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        LOGGER.info("Validating blockchain.");
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                LOGGER.warning("Chain is corrupted!");
                return false;
            }
        }
        LOGGER.info("Chain is valid.");
        return true;
    }

    public Passport getPassport(String id) {
        Iterator<Block> lit = blockchain.descendingIterator();
        while (lit.hasNext()) {
            Block block = lit.next();
            Optional<Transaction> transaction = block.getLastTransactionForPassport(id);
            if (transaction.isPresent()) {
                try {
                    return (Passport) transaction.get().getPassport().clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException("Cannot clone passport.");
                }
            }
        }
        return null;
    }

    public void mineBlock(List<Transaction> transactionList) {
        List<Transaction> transactionsToAdd = List.copyOf(transactionList);
        Block block;
        if (blockchain.isEmpty()) {
            block = new Block(null, transactionsToAdd);
        } else {
            String previousHash = blockchain.getLast().getHash();
            block = new Block(previousHash, transactionsToAdd);
        }
        LOGGER.info("Mining block!");
        block.mineBlock(DIFFICULTY);
        addBlockToBlockchain(block);
    }

    public void addBlockToBlockchain(Block block) {
        LOGGER.info(String.format("Adding block %s to blockchain.", block.getHash()));
        blockchain.add(block);
    }
}
