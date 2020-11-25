package data.builders;

import data.Passport;
import data.components.CountriesPair;
import data.components.Person;
import data.components.Visa;

import java.time.LocalDate;
import java.util.LinkedList;

public final class PassportBuilder {
    private String id;
    private Person person;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String series;
    private String documentNumber;
    private String issuingCountry;
    private LinkedList<Visa> listOfVisas;
    private LinkedList<CountriesPair> listOfBorderCrossings;
    private boolean valid;

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

    public PassportBuilder withIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public PassportBuilder withExpirationDate(LocalDate expirationDate) {
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

    public PassportBuilder withListOfVisas(LinkedList<Visa> listOfVisas) {
        this.listOfVisas = listOfVisas;
        return this;
    }

    public PassportBuilder withListOfBorderCrossings(LinkedList<CountriesPair> listOfBorderCrossings) {
        this.listOfBorderCrossings = listOfBorderCrossings;
        return this;
    }

    public PassportBuilder withValid(boolean valid) {
        this.valid = valid;
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
        passport.setListOfVisas(listOfVisas);
        passport.setListOfBorderCrossings(listOfBorderCrossings);
        passport.setValid(valid);
        return passport;
    }
}
