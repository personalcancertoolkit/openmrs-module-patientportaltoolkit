package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by srikumma on 7/5/17.
 */
public class ChangePasswordFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_CHANGEPASSWORD_FRAGMENT", pageRequest.getRequest()));
    }

    public void saveNewPassword(FragmentModel model,
            @RequestParam(value = "currentPassword", required = true) String currentPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            HttpServletRequest servletRequest) {
        log.info(PPTLogAppender.appendLog("SAVE_NEW_PASSWORD", servletRequest));
        Context.getUserService().changePassword(currentPassword, newPassword);
    }
}
