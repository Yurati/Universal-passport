package data;

import data.components.Person;
import lombok.Data;

import java.util.Date;

@Data
public class Passport {
    private String id;
    private Person person;
    private Date issueDate;
    private Date expirationDate;
    private String series;
    private String documentNumber;
    private String issuingCountry;
}
