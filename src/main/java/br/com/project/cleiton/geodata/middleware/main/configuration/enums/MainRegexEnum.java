package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainRegexEnum {

    OWNERID_REGEX("^1006+\\d{12}$"), VIN_REGEX("^[A-Z0-9]{1,20}$");

    public final String label;

    private MainRegexEnum(String label) {
        this.label = label;
    }
}
