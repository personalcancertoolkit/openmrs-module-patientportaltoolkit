package org.openmrs.module.patientportaltoolkit.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by srikumma on 4/20/17.
 */
@Controller
@RequestMapping(value = "/patientportaltoolkit/**")
public class BaseCancerPortalController {

    private final int DEFAULT_ERROR_CODE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    private static final String DISABLE_WWW_AUTH_HEADER_NAME = "Disable-WWW-Authenticate";

    private final String DEFAULT_ERROR_DETAIL = "";

    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SimpleObject handleException(Exception ex, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int errorCode = DEFAULT_ERROR_CODE;
        String errorDetail = DEFAULT_ERROR_DETAIL;
        ResponseStatus ann = ex.getClass().getAnnotation(ResponseStatus.class);
        if (ann != null) {
            errorCode = ann.value().value();
            if (StringUtils.isNotEmpty(ann.reason())) {
                errorDetail = ann.reason();
            }

        } else if (RestUtil.hasCause(ex, APIAuthenticationException.class)) {
            return RestUtil.wrapErrorResponse(ex, errorDetail);
        } else if (ex.getClass() == HttpRequestMethodNotSupportedException.class) {
            errorCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
        }
        if (errorCode >= 500) {
            // if it's a server error, we log it at a high level of importance
            log.error(ex.getMessage(), ex);
        } else {
            // 4xx client errors are logged at a lower level of importance
            log.info(ex.getMessage(), ex);
        }
        response.setStatus(errorCode);
        return RestUtil.wrapErrorResponse(ex, errorDetail);
    }
}
