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
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalForm;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalFormService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalFormDAO;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */
public class PatientPortalFormServiceImpl extends BaseOpenmrsService implements PatientPortalFormService {

    protected PatientPortalFormDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public PatientPortalFormDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PatientPortalFormDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<PatientPortalForm> getAllPatientPortalForms() {
      return dao.getAllPatientPortalForms();
    }

    @Override
    public PatientPortalForm getPatientPortalForm(String uuid) {
        return dao.getPatientPortalForm(uuid);
    }

    @Override
    public PatientPortalForm getPatientPortalFormByFormType(String formType) {
        return dao.getPatientPortalFormByFormType(formType);
    }
}
