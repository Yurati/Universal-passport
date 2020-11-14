package data;

import common.CountriesPair;
import data.components.Person;
import data.components.Visa;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;

@Data
public class Passport {
    private String id;
    private Person person;
    private Date issueDate;
    private Date expirationDate;
    private String series;
    private String documentNumber;
    private String issuingCountry;
    private LinkedList<Visa> listOfVisas;
    private LinkedList<CountriesPair> listOfBorderCrossing;
    private boolean valid;

    public Passport() {
        listOfVisas = new LinkedList<>();
        listOfBorderCrossing = new LinkedList<>();
    }

    public void invalidate() {
        valid = false;
    }
}
