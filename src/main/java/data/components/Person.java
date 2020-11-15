package data.components;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    private Gender gender;
    private String identityNumber;

}
