package p2p.units;

import blockchain.data.Passport;
import blockchain.transactions.Transaction;
import p2p.PeerInfo;
import p2p.handlers.NewBlockBaseHandler;
import p2p.util.Const;

public abstract class AgentWithNewBlockRights extends Agent {
    public AgentWithNewBlockRights(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);
        addHandler(Const.NEW_BLOCK, new NewBlockBaseHandler(this));
    }

    protected void createNewTransaction(Passport passport) {
        Transaction transaction = new Transaction(publicKey, passport);
        transaction.generateSignature(privateKey);
        transactionLinkedList.add(transaction);
    }
}
