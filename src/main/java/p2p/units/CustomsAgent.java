package p2p.units;

import blockchain.data.Passport;
import p2p.PeerInfo;

public class CustomsAgent extends Agent {

    public CustomsAgent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);
    }

    public boolean isPassportValid(String id) {
        Passport passport = getBlockchain().getPassport(id);
        return passport.isValid();
    }
}
