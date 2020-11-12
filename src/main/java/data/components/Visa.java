package data.components;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Visa {
    private String id;
    private String issuingCountry;
    private Date issueDate;
    private Date expirationDate;
    private List<String> openedCountries;
}
