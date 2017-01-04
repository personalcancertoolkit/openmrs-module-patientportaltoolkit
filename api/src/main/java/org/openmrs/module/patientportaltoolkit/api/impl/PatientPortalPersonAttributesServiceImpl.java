package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalPersonAttributesService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalPersonAttributesDAO;

/**
 * Created by maurya on 1/4/17.
 */
public class PatientPortalPersonAttributesServiceImpl extends BaseOpenmrsService implements PatientPortalPersonAttributesService {

    protected PatientPortalPersonAttributesDAO dao;
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public PatientPortalPersonAttributesDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PatientPortalPersonAttributesDAO dao) {
        this.dao = dao;
    }
    @Override
    public PatientPortalPersonAttributes getPatientPortalPersonAttributesByPatient(Patient patient) {
        return dao.getPatientPortalPersonAttributesByPatient(patient);
    }
}
