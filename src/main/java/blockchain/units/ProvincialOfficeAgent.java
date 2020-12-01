package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;
import blockchain.data.rights.AccessRight;
import p2p.PeerInfo;

public class ProvincialOfficeAgent extends Agent {

    public ProvincialOfficeAgent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);
        accessRightHashSet.add(AccessRight.ADD);
    }

    public void createAddPassportTransaction(Passport passport) {
        createNewTransaction(passport);
    }
}
