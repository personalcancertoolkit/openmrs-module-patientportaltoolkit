package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.patientportaltoolkit.PasswordChangeRequest;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalMiscDAO;

import java.util.List;

/**
 * Created by srikumma on 6/9/17.
 */
public class HibernatePatientPortalMiscDAO implements PatientPortalMiscDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<PasswordChangeRequest> getAllPasswordChangeRequests() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PasswordChangeRequest.class);
        return c.list();
    }

    @Override
    public PasswordChangeRequest getPasswordChangeRequestbyUuid(String uuid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(PasswordChangeRequest.class);
        c.add(Restrictions.eq("uuid", uuid));
        return (PasswordChangeRequest) c.uniqueResult();
    }

    @Override
    public PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest) {
        sessionFactory.getCurrentSession().saveOrUpdate(passwordChangeRequest);
        return passwordChangeRequest;
    }


}
