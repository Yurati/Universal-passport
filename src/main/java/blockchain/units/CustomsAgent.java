package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;

public class CustomsAgent extends Agent {

    public CustomsAgent(Blockchain blockchain) {
        super(blockchain);
    }

    public boolean isPassportValid(String id) {
        Passport passport = blockchain.getPassport(id);
        return passport.isValid();
    }
}
