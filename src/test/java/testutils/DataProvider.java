package testutils;

import data.Passport;
import data.builders.PassportBuilder;
import data.builders.PersonBuilder;
import data.components.Gender;
import data.components.Person;
import lombok.Getter;
import transactions.Transaction;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;

public class DataProvider {
    @Getter
    private PrivateKey privateKey;
    @Getter
    private PublicKey publicKey;

    public DataProvider() {
        generateKeyPair();
    }

    public Passport getPassportDefaultData() {
        Person person = PersonBuilder
                .personBuilder()
                .withBirthDate(new Date())
                .withFirstName("Test")
                .withLastName("Test")
                .withGender(Gender.MALE)
                .withIdentityNumber("1234456")
                .build();

        return PassportBuilder
                .passportBuilder()
                .withId("1")
                .withPerson(person)
                .withSeries("CBD")
                .withDocumentNumber("123456")
                .withExpirationDate(new Date())
                .withIssueDate(new Date())
                .withIssuingCountry("PL")
                .build();
    }

    public Transaction getDefaultTransaction() {
        return new Transaction(publicKey, getPassportDefaultData());
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
