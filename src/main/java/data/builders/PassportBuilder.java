package data.builders;

import data.Passport;
import data.components.Person;

import java.util.Date;

public final class PassportBuilder {
    private String id;
    private Person person;
    private Date issueDate;
    private Date expirationDate;
    private String series;
    private String documentNumber;
    private String issuingCountry;

    private PassportBuilder() {
    }

    public static PassportBuilder passportBuilder() {
        return new PassportBuilder();
    }

    public PassportBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public PassportBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public PassportBuilder withIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public PassportBuilder withExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public PassportBuilder withSeries(String series) {
        this.series = series;
        return this;
    }

    public PassportBuilder withDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public PassportBuilder withIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
        return this;
    }

    public Passport build() {
        Passport passport = new Passport();
        passport.setId(id);
        passport.setPerson(person);
        passport.setIssueDate(issueDate);
        passport.setExpirationDate(expirationDate);
        passport.setSeries(series);
        passport.setDocumentNumber(documentNumber);
        passport.setIssuingCountry(issuingCountry);
        passport.setValid(true);
        return passport;
    }
}
