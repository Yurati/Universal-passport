package units;

import chain.Blockchain;
import data.Passport;
import data.rights.AccessRight;
import lombok.Getter;
import transactions.Transaction;
import utils.KeyUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class Agent {
    @Getter
    protected HashSet<AccessRight> accessRightHashSet;
    protected Blockchain blockchain;
    @Getter
    protected LinkedList<Transaction> transactionLinkedList;

    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public Agent(Blockchain blockchain) {
        accessRightHashSet = new HashSet<>();
        transactionLinkedList = new LinkedList<>();
        accessRightHashSet.add(AccessRight.READ);
        this.blockchain = blockchain;
        KeyPair keyPair = KeyUtils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    protected void createNewTransaction(Passport passport) {
        Transaction transaction = new Transaction(publicKey, passport);
        transaction.generateSignature(privateKey);
        transactionLinkedList.add(transaction);
    }

    protected void addTransactionsToBlockchain() {
        blockchain.mineBlock(transactionLinkedList);
        transactionLinkedList.clear();
    }

    //TODO: impl synchronization between agents
    public void synchronizeBlockchain() {

    }
}
