package data.components;

import lombok.Getter;

import java.util.Date;

@Getter
public class CountriesPair implements Cloneable {
    private String fromCountry;
    private String toCountry;
    private long timestamp;

    public CountriesPair(String fromCountry, String toCountry) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        timestamp = new Date().getTime();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
