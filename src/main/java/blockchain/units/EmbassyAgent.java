package blockchain.units;

import blockchain.chain.Blockchain;
import blockchain.data.Passport;
import blockchain.data.components.Visa;
import blockchain.data.rights.AccessRight;

import java.util.LinkedList;

public class EmbassyAgent extends Agent {
    public LinkedList<Visa> visas;

    public EmbassyAgent(Blockchain blockchain) {
        super(blockchain);
        accessRightHashSet.add(AccessRight.VISA);
        visas = new LinkedList<>();
    }

    public void addVisaToEmbassy(Visa visa) {
        visas.add(visa);
    }

    public void createVisaTransaction(Visa visa, String passportId) {
        Passport passport = blockchain.getPassport(passportId);
        if (visas.contains(visa)) {
            passport.getListOfVisas().add(visa);
        } else {
            throw new RuntimeException("Embassy does not have right to add this visa.");
        }
        createNewTransaction(passport);
    }
}
