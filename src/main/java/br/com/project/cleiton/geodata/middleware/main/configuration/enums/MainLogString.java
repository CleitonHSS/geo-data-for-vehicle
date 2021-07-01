package br.com.project.cleiton.geodata.middleware.main.configuration.enums;

public enum MainLogString {
    REQUEST_RECEIVED("-----------------REQUEST RECEIVED----------------"),
    NOTIFICATION_RECEIVED("--------------NOTIFICATION RECEIVED--------------"),
    CALLBACK_RECEIVED("----------------CALLBACK RECEIVED----------------"),
    REQUEST_SENT("--------------REQUEST HAS BEEN SENT--------------"),
    NOTIFICATION_SENT("------------NOTIFICATION HAS BEEN SENT-----------"),
    CALLBACK_SENT("--------------CALLBACK HAS BEEN SENT-------------"),
    ERROR("----------------------ERROR----------------------"),
    ERROR_RECEIVED("------------------ERROR RECEIVED-----------------"),
    ERROR_CALLBACK("------------------ERROR CALLBACK-----------------"),
    ERROR_CALLBACK_SENT("-----------ERROR CALLBACK HAS BEEN SENT----------"),
    RESPONSE_SENT("--------------RESPONSE HAS BEEN SENT-------------"),
    METHOD_EXECUTED("------------------METHOD EXECUTED----------------");

    public final String label;

    private MainLogString(String label) {
        this.label = label;
    }
}
