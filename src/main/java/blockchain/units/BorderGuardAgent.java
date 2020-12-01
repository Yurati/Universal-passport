package blockchain.units;

import blockchain.data.Passport;
import blockchain.data.components.CountriesPair;
import blockchain.data.rights.AccessRight;
import p2p.PeerInfo;

public class BorderGuardAgent extends Agent {
    private final CountriesPair countriesPair;

    public BorderGuardAgent(int maxPeers, PeerInfo peerInfo, String from, String to) {
        super(maxPeers, peerInfo);
        accessRightHashSet.add(AccessRight.BORDER_CROSS);
        countriesPair = new CountriesPair(from, to);
    }

    public void createAddCountriesPairTransaction(String passportId) {
        Passport passport = getBlockchain().getPassport(passportId);
        passport.getListOfBorderCrossings().add(countriesPair);
        createNewTransaction(passport);
    }
}
