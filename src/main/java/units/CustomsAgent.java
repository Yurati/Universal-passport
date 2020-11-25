package units;

import chain.Blockchain;
import data.Passport;

public class CustomsAgent extends Agent {

    public CustomsAgent(Blockchain blockchain) {
        super(blockchain);
    }

    public boolean isPassportValid(String id) {
        Passport passport = blockchain.getPassport(id);
        return passport.isValid();
    }
}
