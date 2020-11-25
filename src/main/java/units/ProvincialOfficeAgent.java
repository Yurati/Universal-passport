package units;

import chain.Blockchain;
import data.Passport;
import data.rights.AccessRight;
import transactions.Transaction;

public class ProvincialOfficeAgent extends Agent {

    public ProvincialOfficeAgent(Blockchain blockchain) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.ADD);
    }

    public Transaction createAddPassportTransaction(Passport passport) {
        return createNewTransaction(passport);
    }
}
