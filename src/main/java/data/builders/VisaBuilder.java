package data.builders;

import data.components.Visa;

import java.time.LocalDate;
import java.util.List;

public final class VisaBuilder {
    private String id;
    private String issuingCountry;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private List<String> allowedCountries;

    private VisaBuilder() {
    }

    public static VisaBuilder visaBuilder() {
        return new VisaBuilder();
    }

    public VisaBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public VisaBuilder withIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
        return this;
    }

    public VisaBuilder withIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public VisaBuilder withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public VisaBuilder withAllowedCountries(List<String> allowedCountries) {
        this.allowedCountries = allowedCountries;
        return this;
    }

    public Visa build() {
        Visa visa = new Visa();
        visa.setId(id);
        visa.setIssuingCountry(issuingCountry);
        visa.setIssueDate(issueDate);
        visa.setExpirationDate(expirationDate);
        visa.setAllowedCountries(allowedCountries);
        return visa;
    }
}
