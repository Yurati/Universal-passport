package units;

import chain.Blockchain;
import data.Passport;
import data.components.CountriesPair;
import data.rights.AccessRight;
import transactions.Transaction;

public class BorderGuardAgent extends Agent {
    private final CountriesPair countriesPair;

    public BorderGuardAgent(Blockchain blockchain, String from, String to) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.BORDER_CROSS);
        countriesPair = new CountriesPair(from, to);
    }

    public Transaction createAddCountriesPairTransaction(String passportId) {
        Passport passport = blockchain.getPassport(passportId);
        passport.getListOfBorderCrossings().add(countriesPair);
        return createNewTransaction(passport);
    }
}
