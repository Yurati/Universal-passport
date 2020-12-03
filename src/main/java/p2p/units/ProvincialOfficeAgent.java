package p2p.units;

import blockchain.data.Passport;
import blockchain.data.rights.AccessRight;
import p2p.PeerInfo;
import p2p.handlers.NewBlockBaseHandler;
import p2p.util.Const;

public class ProvincialOfficeAgent extends AgentWithNewBlockRights {

    public ProvincialOfficeAgent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);
        addHandler(Const.NEW_BLOCK, new NewBlockBaseHandler(this));
        accessRightHashSet.add(AccessRight.ADD);
    }

    public void createAddPassportTransaction(Passport passport) {
        createNewTransaction(passport);
    }
}
