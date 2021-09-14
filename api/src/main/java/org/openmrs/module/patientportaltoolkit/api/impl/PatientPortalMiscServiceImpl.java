package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.EventLog;
import org.openmrs.module.patientportaltoolkit.PasswordChangeRequest;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalMiscService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalMiscDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by srikumma on 6/9/17.
 */
public class PatientPortalMiscServiceImpl implements PatientPortalMiscService {

    protected PatientPortalMiscDAO dao;


    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public PatientPortalMiscDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PatientPortalMiscDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<PasswordChangeRequest> getAllPasswordChangeRequests() {
        return dao.getAllPasswordChangeRequests();
    }

    @Override
    public PasswordChangeRequest getPasswordChangeRequests(String uuid) {
        return dao.getPasswordChangeRequestbyUuid(uuid);
    }

    @Override
    public PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest) {
        return dao.savePasswordChangeRequest(passwordChangeRequest);
    }

    @Override
    public EventLog logEvent(String event, String eventData) {
        EventLog el = new EventLog(event,eventData, Context.getAuthenticatedUser(),new Date());
        return dao.logEvent(el);
    }
}
