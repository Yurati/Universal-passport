package transactions;

import data.Passport;
import lombok.Getter;
import utils.BlockUtils;
import utils.KeyUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.logging.Logger;

public class Transaction {
    private static final Logger LOGGER = Logger.getLogger("Transaction");
    @Getter
    private final String transactionId;
    private final PublicKey sender;
    @Getter
    private final Passport passport;
    private byte[] signature;
    @Getter
    private final long timestamp;

    private static long sequence = 0; // a rough count of how many transactions have been generated.

    public Transaction(PublicKey from, Passport passport) {
        this.sender = from;
        this.passport = passport;
        transactionId = calculateHash();
        timestamp = new Date().getTime();
    }

    public void generateSignature(PrivateKey privateKey) {
        LOGGER.info("Generating signature.");
        String data = KeyUtils.getStringFromKey(sender) + passport.getId() + passport;
        signature = KeyUtils.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        LOGGER.info("Verifying signature.");
        String data = KeyUtils.getStringFromKey(sender) + passport.getId() + passport;
        return KeyUtils.verifyECDSASig(sender, data, signature);
    }

    private String calculateHash() {
        sequence++;
        return BlockUtils.applySha256(
                KeyUtils.getStringFromKey(sender) +
                        passport.getId() +
                        passport + sequence
        );
    }
}
