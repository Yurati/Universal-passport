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

public abstract class Agent {
    @Getter
    protected HashSet<AccessRight> accessRightHashSet;
    protected Blockchain blockchain;

    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public Agent(Blockchain blockchain) {
        accessRightHashSet = new HashSet<>();
        accessRightHashSet.add(AccessRight.READ);
        this.blockchain = blockchain;
        KeyPair keyPair = KeyUtils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    protected Transaction createNewTransaction(Passport passport) {
        Transaction transaction = new Transaction(publicKey, passport);
        transaction.generateSignature(privateKey);
        return transaction;
    }
}
