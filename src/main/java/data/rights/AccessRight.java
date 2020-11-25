package data.rights;

public enum AccessRight {
    READ("Read"),
    ADD("Add"),
    VISA("Visa"),
    BORDER_CROSS("Border cross");

    String accessRight;

    AccessRight(String accessRight) {
        this.accessRight = accessRight;
    }
}
