package blockchain.data.components;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE("Male"),
    FEMALE("Female");

    String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
