package br.com.project.cleiton.geodata.enums;

public enum ClientHeaderParameter {

    OWNERID("ownerid"), VIN("vin"), REQUESTID("requestId");

    public final String label;

    private ClientHeaderParameter(String label) {
        this.label = label;
    }
}
