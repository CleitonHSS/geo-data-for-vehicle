package br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception;

public enum MainExceptionDescription {

    PARAMETER_EXCEPTION("Invalid Parameter."), EXTERNAL_SERVICE_EXCEPTION("Error from external service."),
    URL_PARAMETER_EXCEPTION("No required parameter"), HEADER_PARAMETER_EXCEPTION("No required header parameter"),
    MESSAGE_EXCEPTION("Invalid broker message"),
    TRANSPORT_EXCEPTION("Error - It was not possible to send data to broker."),
    MESSAGE_PROCESSING_ERROR("The application received an error while processing the listened message."),
    INTERNAL_SERVER_ERROR("Error - Internal server error");

    public final String label;

    private MainExceptionDescription(String label) {
        this.label = label;
    }
}
