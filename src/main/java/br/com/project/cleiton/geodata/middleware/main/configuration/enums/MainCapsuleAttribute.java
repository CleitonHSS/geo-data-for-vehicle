package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainCapsuleAttribute {
    // request or response or notification
    TYPE("TYPE"),
    // request or response endpoint (ex: car-find-location)
    ENDPOINT("ENDPOINT"),
    // Request Origin Path in URL stale. It should be incremented in each api where the request
    // travel(ex:ejfnqejwnkjfnkej)
    PATH("PATH"),
    // Unique Uuid ID for each request. It should be setted at request fist origin
    REQUESTID("REQUEST-UUID"),
    // Response status (In case of request type it shoud be setted as "000")
    STATUS("STATUS"),
    // Message Origin
    ORIGIN("ORIGIN"),
    // Message Destination
    DESTINATION("DESTINATION"),
    // Json in specific format according to ENDPOINT
    BODY("BODY"),
    // Main capsule Attributes hashmap
    ATTRIBUTES("ATTRIBUTES"),
    // Request/Response parameters hashmap
    PARAMETERS("PARAMETERS");

    public final String label;

    private MainCapsuleAttribute(String label) {
        this.label = label;
    }
}
