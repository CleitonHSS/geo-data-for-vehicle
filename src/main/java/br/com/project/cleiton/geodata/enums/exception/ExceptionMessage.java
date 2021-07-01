package br.com.project.cleiton.geodata.enums.exception;

public enum ExceptionMessage {

    PARAMETER_EXCEPTION_OWNERID(
            "Invalid OwnerId - It should be: Numeric String; Length: 16; Value/Format:(4 digits prefix)[1006]+[000000000001-999999999999]"),
    PARAMETER_EXCEPTION_VIN("Invalid VIN - It should be: Alphanumeric String (a-zA-Z0-9); Length: 0<,<=20");

    public final String label;

    private ExceptionMessage(String label) {
        this.label = label;
    }
}
