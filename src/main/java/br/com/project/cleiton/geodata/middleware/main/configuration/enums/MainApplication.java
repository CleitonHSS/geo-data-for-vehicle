package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainApplication {

    CAR_INFORMATION("car-information"), GEOLOCALIZATION("geodata"), VEHICLE_DATA_HUB("vehicle-data-hub"),
    BFF_ANDROID("bff-android"), BFF_IPHONE("bff-iphone"), TELEMETRIC("telemetric"), INTEGRATION("integration"),
    USERS("users"), ALARM_NOTIFICATION("alarm-notification");

    public final String label;

    private MainApplication(String label) {
        this.label = label;
    }
}
