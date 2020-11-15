package common;

import lombok.Getter;

import java.util.Date;

@Getter
public class CountriesPair {
    private String fromCountry;
    private String toCountry;
    private long timestamp;

    public CountriesPair(String fromCountry, String toCountry) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        timestamp = new Date().getTime();
    }
}