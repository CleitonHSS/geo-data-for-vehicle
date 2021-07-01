package br.com.project.cleiton.geodata.enums;

public enum ClientEndpoint {
    GEOFENCING_STATUS("/geo-fencing/status"), GEOFENCING("/geo-fencing/request"),
    SPEED_ALERT_STATUS("/speed-alert/status"), SPEED_ALERT("/speed-alert/request");

    public final String label;

    private ClientEndpoint(String label) {
        this.label = label;
    }
}
