package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.module.patientportaltoolkit.PasswordChangeRequest;

import java.util.List;

/**
 * Created by srikumma on 6/9/17.
 */
public interface PatientPortalMiscDAO {
     List<PasswordChangeRequest> getAllPasswordChangeRequests();

    PasswordChangeRequest getPasswordChangeRequestbyUuid(String uuid);

    PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest);
}
