package transactions;

import chain.Blockchain;
import data.Passport;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final Logger LOGGER = Logger.getLogger("Storage");
    private PrivateKey privateKey;
    public PublicKey publicKey;
    public HashMap<String, Transaction> storageTransactions;
    private Passport passport;

    public Storage(Passport passport) {
        generateKeyPair();
        storageTransactions = new HashMap<>();
        this.passport = passport;
    }

    public void updatePassport(ArrayList<Transaction> transactions){
        //TODO: add update for passport data based on transactions
    }

    private void generateKeyPair() {
        try {
            LOGGER.info("Generating key pair");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while creating key pair!");
            throw new RuntimeException(e);
        }
    }
}
