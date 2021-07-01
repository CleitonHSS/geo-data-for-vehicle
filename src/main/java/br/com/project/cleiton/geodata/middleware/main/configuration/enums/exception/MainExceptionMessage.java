package br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception;

public enum MainExceptionMessage {

    MESSAGE_UNKNOWN_ERROR("An unknown internal error has occurred"),
    MESSAGE_INVALID_ATRIBUT("Invalid attribute- '%s' is a invalid %s"),
    MESSAGE_NULL_ATRIBUT("Null attribute - '%s' is null"),
    INVALID_TYPE_OR_ENDPOINT("No valid message type or endpoint!(Type: %s , Endpoint: %s)"),
    PARAMETER_EXCEPTION_OWNERID(
            "Invalid OwnerId - It should be: Numeric String; Length: 16; Value/Format:(4 digits prefix)[1006]+[000000000001-999999999999]"),
    PARAMETER_EXCEPTION_VIN("Invalid VIN - It should be: Alphanumeric String (a-zA-Z0-9); Length: 0<,<=20"),
    PARAMETER_REQUIRED_EXCEPTION("Parameter '%s' is required!");

    public final String label;

    private MainExceptionMessage(String label) {
        this.label = label;
    }
}
