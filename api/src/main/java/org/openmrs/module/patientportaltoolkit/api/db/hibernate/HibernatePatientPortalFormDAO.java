package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public void savePatientPortalForm(PatientPortalForm patientPortalForm) {

    }

    @Override
    public void softDeletePatientPortalForm(PatientPortalForm patientPortalForm) {

    }
}
