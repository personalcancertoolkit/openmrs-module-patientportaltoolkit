package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.User;
import org.openmrs.module.patientportaltoolkit.EventLog;
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

        @SuppressWarnings("unchecked")
        List<PasswordChangeRequest> list = c.list();
        return list;
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

    @Override
    public EventLog logEvent(EventLog el) {
        sessionFactory.getCurrentSession().saveOrUpdate(el);
        return el;
    }

    @Override
    public EventLog getLatestAppointmentReminderHasRunEventLog() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(EventLog.class);
        c.add(Restrictions.eq("event", EventLog.APPOINTMENT_REMINDER_RUN));
        c.addOrder(Order.desc("createdAt"));

        @SuppressWarnings("unchecked")
        List<EventLog> list = c.list();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public EventLog getLatestAppointmentReminderNotificationSentForUser(User user) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(EventLog.class);
        c.add(Restrictions.eq("event", EventLog.APPOINTMENT_REMINDER_NOTIFICATION_SENT));
        c.add(Restrictions.eq("user", user));
        c.addOrder(Order.desc("createdAt"));

        @SuppressWarnings("unchecked")
        List<EventLog> list = c.list();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

}
