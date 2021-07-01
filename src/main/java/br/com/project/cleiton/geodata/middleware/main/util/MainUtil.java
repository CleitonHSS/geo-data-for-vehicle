package br.com.project.cleiton.geodata.middleware.main.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainLogString;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainRegexEnum;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.MainTimeFormat;
import br.com.project.cleiton.geodata.middleware.main.configuration.enums.exception.MainExceptionMessage;
import br.com.project.cleiton.geodata.middleware.main.exception.MainErrorCallbackController;
import br.com.project.cleiton.geodata.middleware.main.exception.MainParameterException;
import br.com.project.cleiton.geodata.middleware.main.domain.MainMessageCapsule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MainUtil {

    @Autowired
    private MainErrorCallbackController cleitonErrorCallback;

    public static String fileToStringMock(String fileInPath) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileInPath)));
        } catch (IOException e) {
            log.error(MainLogString.ERROR.label);
            log.error(e.getMessage());
            throw new MainParameterException(e.getMessage());
        }
    }

    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error(MainLogString.ERROR.label);
            log.error(e.getMessage());
            throw new MainParameterException(e.getMessage());
        }
    }

    public static <T> T jsonToObject(String json, Class<T> objectClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T object = mapper.readValue(json, objectClass);
            return object;
        } catch (IOException e) {
            log.error(MainLogString.ERROR.label);
            log.error(e.getMessage());
            throw new MainParameterException(e.getMessage());
        }
    }

    public static String getXEventTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(MainTimeFormat.EVENT_TIME_MS.label);
        return formatter.format(date);
    }

    public static Boolean checkParameterPattern(String parameter, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return parameter == null ? false : pattern.matcher(parameter).matches();
    }

    public void checkOwnerVinIdsForAsyncRequest(MainMessageCapsule cleitonMessageCapsule) {

        HashMap<String, String> ExParameters = cleitonMessageCapsule.getParameters();

        if (!checkParameterPattern(ExParameters.get("ownerId"), MainRegexEnum.OWNERID_REGEX.label)) {
            MainParameterException ownerIdError = new MainParameterException(
                    MainExceptionMessage.PARAMETER_EXCEPTION_OWNERID.label);
            cleitonErrorCallback.sendException(cleitonMessageCapsule, ownerIdError);
            throw ownerIdError;
        }
        if (!checkParameterPattern(ExParameters.get("vin"), MainRegexEnum.VIN_REGEX.label)) {
            MainParameterException vinError = new MainParameterException(
                    MainExceptionMessage.PARAMETER_EXCEPTION_VIN.label);
            cleitonErrorCallback.sendException(cleitonMessageCapsule, vinError);
            throw vinError;
        }
    }

    public void checkOwnerVinIdsForSyncRequest(String ownerId, String vin) {

        if (!checkParameterPattern(ownerId, MainRegexEnum.OWNERID_REGEX.label)) {
            throw new MainParameterException(MainExceptionMessage.PARAMETER_EXCEPTION_OWNERID.label);
        }
        if (vin != null) {
            if (!checkParameterPattern(vin, MainRegexEnum.VIN_REGEX.label)) {
                throw new MainParameterException(MainExceptionMessage.PARAMETER_EXCEPTION_VIN.label);
            }
        }
    }

    public void checkNullParameter(MainMessageCapsule capsule, String Parameter, String key) {
        if (Parameter == null || Parameter.trim().isEmpty()) {
            String errorMassege = String.format(MainExceptionMessage.PARAMETER_REQUIRED_EXCEPTION.label, key);
            MainParameterException error = new MainParameterException(errorMassege);
            cleitonErrorCallback.sendException(capsule, error);
            throw error;
        }
    }

    public String checkNullParameterAndReturnValue(MainMessageCapsule capsule, String parameter, String key) {
        if (parameter == null || parameter.trim().isEmpty()) {
            String errorMassege = String.format(MainExceptionMessage.PARAMETER_REQUIRED_EXCEPTION.label, key);
            MainParameterException error = new MainParameterException(errorMassege);
            if (capsule != null)
                cleitonErrorCallback.sendException(capsule, error);
            throw error;
        }
        return parameter;
    }

}
