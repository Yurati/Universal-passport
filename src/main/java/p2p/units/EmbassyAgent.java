package p2p.units;

import blockchain.data.Passport;
import blockchain.data.components.Visa;
import blockchain.data.rights.AccessRight;
import blockchain.exceptions.InvalidVisaTypeException;
import p2p.PeerInfo;
import p2p.handlers.NewBlockBaseHandler;
import p2p.util.Const;

import java.util.LinkedList;

public class EmbassyAgent extends AgentWithNewBlockRights {
    public LinkedList<Visa> visas;

    public EmbassyAgent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);
        addHandler(Const.NEW_BLOCK, new NewBlockBaseHandler(this));
        accessRightHashSet.add(AccessRight.VISA);
        visas = new LinkedList<>();
    }

    public void addVisaToEmbassy(Visa visa) {
        visas.add(visa);
    }

    public void createVisaTransaction(Visa visa, String passportId) throws InvalidVisaTypeException {
        Passport passport = getBlockchain().getPassport(passportId);
        if (visas.contains(visa)) {
            passport.getListOfVisas().add(visa);
        } else {
            throw new InvalidVisaTypeException("Embassy does not have right to add visa.");
        }
        createNewTransaction(passport);
    }
}
