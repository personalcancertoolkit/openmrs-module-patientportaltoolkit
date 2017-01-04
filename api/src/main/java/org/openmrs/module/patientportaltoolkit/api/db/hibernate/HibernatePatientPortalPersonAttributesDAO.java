package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PatientPortalPersonAttributes;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalPersonAttributesDAO;

/**
 * Created by maurya on 1/4/17.
 */
public class HibernatePatientPortalPersonAttributesDAO implements PatientPortalPersonAttributesDAO {
    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PatientPortalPersonAttributes getPatientPortalPersonAttributesByPatient(Patient patient) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PatientPortalPersonAttributes.class);
        c.add(Restrictions.eq("patient", patient));
        return (PatientPortalPersonAttributes) c.uniqueResult();
    }
}
