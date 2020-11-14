package common;

import lombok.Getter;

@Getter
public class CountriesPair {
    private String fromCountry;
    private String toCountry;

    public CountriesPair(String fromCountry, String toCountry) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
    }
}
