package data;

import common.CountriesPair;
import data.components.Person;
import data.components.Visa;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedList;

@Data
public class Passport {
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

    public Passport() {
        listOfVisas = new LinkedList<>();
        listOfBorderCrossings = new LinkedList<>();
    }

    public void invalidate() {
        valid = false;
    }
}
