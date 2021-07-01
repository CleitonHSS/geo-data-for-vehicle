package br.com.project.cleiton.geodata.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.project.cleiton.geodata.domain.GoogleError;
import br.com.project.cleiton.geodata.middleware.main.exception.MainExternalServiceException;
import br.com.project.cleiton.geodata.middleware.main.util.MainUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class GoogleMapsApi {

    @Value("${googlemaps.url}")
    private String GOOGLE_MAPS_URL;

    @Value("${googlemaps.key}")
    private String KEY;

    @Value("${googlemaps.outputform}")
    private String OUTPUT_FORM;

    private final OkHttpClient httpClient = new OkHttpClient();

    public String getDirections(String origin, String destination, String mode) {

        final String parameters = String.format("%s?origin=%s&destination=%s&mode=%s&key=%s", OUTPUT_FORM, origin,
                destination, mode, KEY);
        final String url = GOOGLE_MAPS_URL + "/directions/" + parameters;

        Request request = new Request.Builder().url(url).build();

        return newCall(request);

    }

    public String getGeocode(String latitude, String longitude) {

        final String parameters = String.format("%s?latlng=%s,%s&key=%s", OUTPUT_FORM, latitude, longitude, KEY);
        final String url = GOOGLE_MAPS_URL + "/geocode/" + parameters;

        Request request = new Request.Builder().url(url).build();

        return newCall(request);

    }

    private String checkResponseBody(Response response) throws IOException {
        String responseBody = response.body().string();

        if (responseBody.contains("error_message") || response.code() != 200) {
            GoogleError googleError = MainUtil.jsonToObject(responseBody, GoogleError.class);
            MainExternalServiceException cleitonExternalException = new MainExternalServiceException(
                    googleError.getStatus() + " - " + googleError.getErrorMessage());
            throw cleitonExternalException;
        }
        return responseBody;
    }

    private String newCall(Request request) {
        try {
            Response response = httpClient.newCall(request).execute();
            return checkResponseBody(response);
        } catch (IOException e) {
            MainExternalServiceException cleitonExternalException = new MainExternalServiceException(e.getMessage());
            throw cleitonExternalException;
        }

    }
}
