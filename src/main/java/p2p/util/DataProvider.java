package p2p.util;

import blockchain.data.Passport;
import blockchain.data.builders.PassportBuilder;
import blockchain.data.builders.PersonBuilder;
import blockchain.data.builders.VisaBuilder;
import blockchain.data.components.Gender;
import blockchain.data.components.Person;
import blockchain.data.components.Visa;
import blockchain.transactions.Transaction;
import blockchain.utils.KeyUtils;
import lombok.Getter;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;

public class DataProvider {
    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.parse("2015-12-03");
    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.parse("2025-12-03");
    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.parse("1995-12-03");
    @Getter
    private final PrivateKey privateKey;
    @Getter
    private final PublicKey publicKey;

    public DataProvider() {
        KeyPair keyPair = KeyUtils.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    public Passport getPassportDefaultData() {

        return passportBuilderWithDefaultData().build();
    }

    public Passport getPassportWithVisaData() {
        LinkedList<Visa> visaList = new LinkedList<>();
        visaList.add(getDefaultVisa());
        return passportBuilderWithDefaultData()
                .withListOfVisas(visaList)
                .build();
    }

    public Transaction getDefaultTransaction() {
        return new Transaction(publicKey, getPassportDefaultData());
    }

    private PassportBuilder passportBuilderWithDefaultData() {
        Person person = PersonBuilder
                .personBuilder()
                .withBirthDate(DEFAULT_BIRTHDATE)
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
                .withExpirationDate(DEFAULT_EXPIRATION_DATE)
                .withIssueDate(DEFAULT_ISSUE_DATE)
                .withIssuingCountry("PL")
                .withListOfBorderCrossings(new LinkedList<>())
                .withValid(true);
    }

    private Visa getDefaultVisa() {
        return VisaBuilder
                .visaBuilder()
                .withId("123")
                .withIssuingCountry("US")
                .withIssueDate(DEFAULT_ISSUE_DATE)
                .withExpirationDate(DEFAULT_EXPIRATION_DATE)
                .withAllowedCountries(Arrays.asList("PL", "GB"))
                .build();

    }

}
