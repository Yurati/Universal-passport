package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;
import blockchain.data.rights.AccessRight;
import blockchain.transactions.Transaction;
import blockchain.utils.KeyUtils;
import lombok.Getter;
import p2p.Node;
import p2p.PeerInfo;
import p2p.handlers.AddBaseHandler;
import p2p.handlers.NewBlockBaseHandler;
import p2p.handlers.QuitBaseHandler;
import p2p.handlers.ResponseBaseHandler;
import p2p.util.Const;
import p2p.util.Router;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class Agent extends Node {
    @Getter
    protected HashSet<AccessRight> accessRightHashSet;
    @Getter
    protected LinkedList<Transaction> transactionLinkedList;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public Agent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);

        addRouter(new Router(this));
        addHandler(Const.QUIT, new QuitBaseHandler(this));
        addHandler(Const.ADD_PEAR, new AddBaseHandler(this));
        addHandler(Const.NEW_BLOCK, new NewBlockBaseHandler(this));
        addHandler(Const.REPLY, new ResponseBaseHandler(this));

        accessRightHashSet = new HashSet<>();
        transactionLinkedList = new LinkedList<>();
        accessRightHashSet.add(AccessRight.READ);
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
        getBlockchain().mineBlock(transactionLinkedList);
        transactionLinkedList.clear();
    }

    //TODO: impl synchronization between agents
    public void synchronizeBlockchain() {

    }
}
