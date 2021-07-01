package br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception;

public enum MainExceptionType {

    PARAMETER_EXCEPTION("PARAMETER-EXCEPTION"), PATH_EXCEPTION("PATH-EXCEPTION"),
    MESSAGE_EXCEPTION("MESSAGE-EXCEPTION"), TRANSPORT_EXCEPTION("TRANSPORT_EXCEPTION"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"), EXTERNAL_ERROR("EXTERNAL_ERROR");

    public final String label;

    private MainExceptionType(String label) {
        this.label = label;
    }
}
