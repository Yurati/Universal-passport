package data.components;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Visa {
    private String id;
    private String issuingCountry;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private List<String> allowedCountries;
}
