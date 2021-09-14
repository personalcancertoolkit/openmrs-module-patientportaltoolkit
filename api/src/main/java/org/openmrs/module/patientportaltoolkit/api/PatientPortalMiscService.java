package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.module.patientportaltoolkit.EventLog;
import org.openmrs.module.patientportaltoolkit.PasswordChangeRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by srikumma on 6/9/17.
 */
public interface PatientPortalMiscService {

    @Transactional(readOnly = true)
    List<PasswordChangeRequest> getAllPasswordChangeRequests();

    @Transactional(readOnly = true)
    PasswordChangeRequest getPasswordChangeRequests(String uuid);

    @Transactional(readOnly = false)
    PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest);

    @Transactional(readOnly = false)
    EventLog logEvent(String event, String data);

}
