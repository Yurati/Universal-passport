package data.builders;

import data.components.Gender;
import data.components.Person;

import java.util.Date;

public final class PersonBuilder {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String identityNumber;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder withBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PersonBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder withIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
        return this;
    }

    public Person build() {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        person.setGender(gender);
        person.setIdentityNumber(identityNumber);
        return person;
    }
}