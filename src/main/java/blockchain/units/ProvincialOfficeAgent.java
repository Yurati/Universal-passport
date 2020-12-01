package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;
import blockchain.data.rights.AccessRight;

public class ProvincialOfficeAgent extends Agent {

    public ProvincialOfficeAgent(Blockchain blockchain) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.ADD);
    }

    public void createAddPassportTransaction(Passport passport) {
        createNewTransaction(passport);
    }
}
