package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainMessageType {

    REQUEST("REQUEST"), CALLBACK("CALLBACK"), NOTIFICATION("NOTIFICATION"), ERROR("ERROR");

    public final String label;

    private MainMessageType(String label) {
        this.label = label;
    }
}
