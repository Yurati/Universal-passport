package chain;

import data.Passport;
import lombok.Getter;
import transactions.Transaction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    public void addBlockToBlockchain(Block block) {
        LOGGER.info(String.format("Adding block %s to blockchain.", block.getHash()));
        blockchain.add(block);
    }

    public Passport getPassport(String id) {
        List<Transaction> transactionList = new ArrayList<>();
        for (Block block : blockchain) {
            List<Transaction> transactionsInBlock = block.getTransactionsForPassport(id);
            if (!transactionsInBlock.isEmpty()) {
                transactionList.addAll(transactionsInBlock);
            }
        }
        return generatePassport(transactionList);
    }

    public String getHashOfLastBlock() {
        return blockchain.getLast().getHash();
    }

    public Block mineBlock(String previousHash, List<Transaction> transactionList) {
        Block block = new Block(previousHash, transactionList);
        LOGGER.info("Mining block!");
        block.mineBlock(DIFFICULTY);
        return block;
    }

    private Passport generatePassport(List<Transaction> transactions) {
        //TODO: generate passport data based on transactions
        return null;
    }
}
