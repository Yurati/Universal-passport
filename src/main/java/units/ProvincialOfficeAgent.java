package units;

import chain.Blockchain;
import data.Passport;
import data.rights.AccessRight;

public class ProvincialOfficeAgent extends Agent {

    public ProvincialOfficeAgent(Blockchain blockchain) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.ADD);
    }

    public void createAddPassportTransaction(Passport passport) {
        createNewTransaction(passport);
    }
}
