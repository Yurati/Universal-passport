package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;
import blockchain.data.components.CountriesPair;
import blockchain.data.rights.AccessRight;

public class BorderGuardAgent extends Agent {
    private final CountriesPair countriesPair;

    public BorderGuardAgent(Blockchain blockchain, String from, String to) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.BORDER_CROSS);
        countriesPair = new CountriesPair(from, to);
    }

    public void createAddCountriesPairTransaction(String passportId) {
        Passport passport = blockchain.getPassport(passportId);
        passport.getListOfBorderCrossings().add(countriesPair);
        createNewTransaction(passport);
    }
}
