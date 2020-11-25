package units;

import chain.Blockchain;
import data.Passport;
import data.components.Visa;
import data.rights.AccessRight;
import transactions.Transaction;

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

    public Transaction createVisaTransaction(Visa visa, String passportId) {
        Passport passport = blockchain.getPassport(passportId);
        if (visas.contains(visa)) {
            passport.getListOfVisas().add(visa);
        } else {
            throw new RuntimeException("Embassy does not have right to add this visa.");
        }
        return createNewTransaction(passport);
    }
}
