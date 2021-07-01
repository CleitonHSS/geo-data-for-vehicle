package br.com.project.cleiton.geodata.carlocation;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.project.cleiton.geodata.client.GoogleMapsApi;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainApplication;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainMessageType;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import br.com.project.cleiton.geodata.middleware.main.service.impl.MainService;
import br.com.project.cleiton.geodata.middleware.main.util.MainUtil;
import br.com.project.cleiton.geodata.service.impl.GeoDataServiceImpl;

@ExtendWith(SpringExtension.class)
public class CarLocationTest {

    @InjectMocks
    private GeoDataServiceImpl geodataService;

    @Mock
    private GoogleMapsApi googleMapsApiMock;
    @Mock
    private MainService cleitonServiceMock;

    private final static String fileInPath = "src/test/resources/carlocation/";

    private MainMessageCapsule getMessageCapsuleByStatus(String status) {
        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("vin", "1006000000000003");
        parameters.put("ownerId", "T00A00009SA000003");
        parameters.put("test", "This is a test from Telemetric");
        parameters.put("requestId", UUID.randomUUID().toString());
        parameters.put("status", status);
        parameters.put("reason", "W000");
        String bodyFromGtc = "";

        if (status.equals("success"))
            bodyFromGtc = MainUtil.fileToStringMock(fileInPath + "car-finder-location.json");

        return MainMessageCapsule.builder().type(MainMessageType.REQUEST.label)
                .path("//" + MainApplication.BFF_ANDROID.label).endpoint("car-location").parameters(parameters)
                .body(bodyFromGtc).requestId(UUID.randomUUID().toString()).build();
    }

    @DisplayName("Carlocation - Check getDirections() method must call Direction API and return a Json valid")
    @Test
    public void getRequestDirectionTest() {

        // Given
        HashMap<String, String> parameters = new HashMap<>();

        String origin = "-23.2845432,-47.2883754";
        String destination = "-23.2735391,-47.2978028";
        String mode = "driving";

        parameters.put("origin", origin);
        parameters.put("destination", destination);
        parameters.put("mode", mode);

        String bodyRawFromGtc = MainUtil.fileToStringMock(fileInPath + "GoogleMapsDirections.json");
        when(assertDoesNotThrow(() -> googleMapsApiMock.getDirections(origin, destination, mode)))
                .thenReturn(bodyRawFromGtc);
        // When
        String directionsResponseBody = geodataService.getDirections(parameters);
        // Then
        assertTrue(directionsResponseBody.contains("geocoded_waypoints"));
    }

    @DisplayName("RemoteEngine - CallBack Status Success ")
    @Test
    public void requestCarLocationCallBackStausSuccessTest() {
        // Given
        MainMessageCapsule messageCapsuleMock = getMessageCapsuleByStatus("success");
        // When
        assertDoesNotThrow(() -> geodataService.sendCarLocationCallBack(messageCapsuleMock));
        // Then
        verify(cleitonServiceMock, only()).sendCallback(messageCapsuleMock);
        assertTrue(messageCapsuleMock.getBody().contains("\"status\":\"success\""));
    }

    @DisplayName("RemoteEngine - CallBack Status Error ")
    @Test
    public void requestCarLocationCallBackStatusErrorTest() {
        // Given
        MainMessageCapsule messageCapsuleMock = getMessageCapsuleByStatus("error");
        // When
        assertDoesNotThrow(() -> geodataService.sendCarLocationCallBack(messageCapsuleMock));
        // Then
        verify(cleitonServiceMock, only()).sendCallback(messageCapsuleMock);
        assertTrue(messageCapsuleMock.getBody().contains("\"status\":\"error\""));
    }

    @DisplayName("RemoteEngine - CallBackError ")
    @Test
    public void requestCarLocationCallBackErrorTest() {
        // Given
        MainMessageCapsule messageCapsuleMock = getMessageCapsuleByStatus("error");
        // When
        assertDoesNotThrow(() -> geodataService.sendCarLocationCallBack(messageCapsuleMock));
        // Then
        verify(cleitonServiceMock, only()).sendCallback(messageCapsuleMock);
        assertTrue(messageCapsuleMock.getBody().contains("\"status\":\"error\""));
    }

}
