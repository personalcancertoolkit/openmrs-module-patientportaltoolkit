/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
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
