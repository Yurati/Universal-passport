package blockchain.data.components;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class CountriesPair implements Cloneable, Serializable {
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
