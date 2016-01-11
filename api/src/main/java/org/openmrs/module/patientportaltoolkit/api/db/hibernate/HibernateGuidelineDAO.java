package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.openmrs.Concept;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.api.db.GuidelineDAO;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public class HibernateGuidelineDAO implements GuidelineDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void deleteGuideline(Guideline guideline) {

    }

    @Override
    public List<Guideline> getAllGuidelines() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        return c.list();
    }

    @Override
    public void saveGuideline(Guideline guideline) {

    }
    @Override
    public  List<Guideline> getGuidelinesbyConditions(List<Concept> conditions) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        return c.list();
    }
}
