package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.PersonPreferences;
import org.openmrs.module.patientportaltoolkit.api.db.PersonPreferencesDAO;

import java.util.List;

/**
 * Created by maurya on 9/19/16.
 */
public class HibernatePersonPreferencesDAO implements PersonPreferencesDAO {

    protected final Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PersonPreferences getPersonPreferenceByUuid(String uuid) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PersonPreferences.class);
        crit.add(Restrictions.eq("uuid", uuid));
        return (PersonPreferences) crit.uniqueResult();
    }

    @Override
    public PersonPreferences savePersonPreferences(PersonPreferences personPreferences) {
        sessionFactory.getCurrentSession().saveOrUpdate(personPreferences);
        return personPreferences;
    }

    @Override
    public void deletePersonPreferences(PersonPreferences personPreferences) {
        sessionFactory.getCurrentSession().delete(personPreferences);
    }

    @Override
    public List<PersonPreferences> getAllPersonPreferences() {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PersonPreferences.class);
        return crit.list();
    }

    @Override
    public PersonPreferences getPersonPreferencesByPerson(Person person) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PersonPreferences.class);
        crit.add(Restrictions.eq("person", person));
        return (PersonPreferences) crit.uniqueResult();
    }
}
