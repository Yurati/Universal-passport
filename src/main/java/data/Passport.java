package data;

import common.CountriesPair;
import data.components.Person;
import data.components.Visa;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Passport {
    private String id;
    private Person person;
    private Date issueDate;
    private Date expirationDate;
    private String series;
    private String documentNumber;
    private String issuingCountry;
    private List<Visa> listOfVisas;
    private List<CountriesPair> listOfBorderCrossing;
}
