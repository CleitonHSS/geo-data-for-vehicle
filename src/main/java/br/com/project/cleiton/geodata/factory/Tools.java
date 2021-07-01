package br.com.project.cleiton.geodata.factory;

public class Tools {
    public static int convertStringToInt(String number) {
        return Integer.decode(number);
    }

    public static Double convertStringToDouble(String number) {
        return Double.valueOf(number);
    }
}
