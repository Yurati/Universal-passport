package data.components;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Visa implements Cloneable {
    private String id;
    private String issuingCountry;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private List<String> allowedCountries;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
