package common;

import lombok.Getter;

public class CountriesPair {
    @Getter
    private String fromCountry;
    @Getter
    private String toCountry;

    public CountriesPair(String fromCountry, String toCountry) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
    }
}
