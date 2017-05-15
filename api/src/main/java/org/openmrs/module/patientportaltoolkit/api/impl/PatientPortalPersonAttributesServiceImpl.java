/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
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

    @Override
    public PatientPortalPersonAttributes savePatientPortalPersonAttributes(PatientPortalPersonAttributes patientPortalPersonAttributes) {
        return dao.savePatientPortalPersonAttributes(patientPortalPersonAttributes);
    }
}
