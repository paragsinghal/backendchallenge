package com.retailio.backendchallenge.utils;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author parag
 *
 *Global exception handler for spring container.
 *Any Exception will basicallly go through this.
 */
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LogFactory logfactory;

    private final Logger logger = LogFactory.getLogger(GlobalExceptionHandler.class);

    private final Gson gson = new Gson();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public String handleIAEException(Exception ex) {
        logger.info("ERROR ", ex);//Not using .error as it will flood sentry
        return toJSONString(ErrorCode.SERVICE_ERROR.name(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public String handleNullPointerException(Exception ex) {
        logger.error("ERROR ", ex);//Not using .error as it will flood sentry
        return toJSONString(ErrorCode.SERVICE_ERROR.name(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handleException(Exception ex) {
        logger.info("ERROR ", ex);//Not using .error as it will flood sentry
        return toJSONString(ErrorCode.SERVICE_ERROR.name(), ex.getMessage());
    }

    private String toJSONString(String errorCode, String message) {

        JsonObject jsonObject = new JsonObject();
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = ErrorCode.SERVICE_ERROR.name();
        }
        jsonObject.addProperty("errorCode", errorCode);
        jsonObject.addProperty("errorMessage", message);

        return gson.toJson(jsonObject);
    }
}

