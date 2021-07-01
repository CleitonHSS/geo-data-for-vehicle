package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainTimeFormat {

    EVENT_TIME("yyyy-MM-dd'T'HH:mm:ss'+00:00'"), EVENT_TIME_MS("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public final String label;

    private MainTimeFormat(String label) {
        this.label = label;
    }
}
