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

package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.PatientPortalForm;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalFormDAO;

import java.util.List;

/**
 * Created by Maurya on 01/06/2015.
 */
public class HibernatePatientPortalFormDAO implements PatientPortalFormDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deletePatientPortalForm(PatientPortalForm patientPortalForm) {

    }

    @Override
    public List<PatientPortalForm> getAllPatientPortalForms() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientPortalForm.class);
        return c.list();
    }

    @Override
    public PatientPortalForm getPatientPortalForm(String uuid) {

        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientPortalForm.class);
        c.add(Restrictions.eq("uuid", uuid));
        return (PatientPortalForm) c.uniqueResult();
    }

    @Override
    public PatientPortalForm getPatientPortalFormByFormType(String formType) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientPortalForm.class);
        c.add(Restrictions.eq("name", formType));
        return (PatientPortalForm) c.uniqueResult();
    }

    @Override
    public void savePatientPortalForm(PatientPortalForm patientPortalForm) {

    }

    @Override
    public void softDeletePatientPortalForm(PatientPortalForm patientPortalForm) {

    }
}
