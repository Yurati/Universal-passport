package blockchain.data;

import blockchain.data.components.CountriesPair;
import blockchain.data.components.Person;
import blockchain.data.components.Visa;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;

@Data
public class Passport implements Cloneable, Serializable {
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

    public void invalidate() {
        valid = false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Passport passport = (Passport) super.clone();
        passport.person = (Person) person.clone();
        passport.listOfVisas = new LinkedList<>(listOfVisas);
        passport.listOfBorderCrossings = new LinkedList<>(listOfBorderCrossings);
        return passport;
    }
}
