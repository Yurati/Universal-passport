package blockchain.data.components;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Person implements Cloneable, Serializable {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    private Gender gender;
    private String identityNumber;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
