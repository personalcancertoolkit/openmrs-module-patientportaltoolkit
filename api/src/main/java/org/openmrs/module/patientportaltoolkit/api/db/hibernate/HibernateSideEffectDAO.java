package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.openmrs.module.patientportaltoolkit.SideEffect;
import org.openmrs.module.patientportaltoolkit.api.db.SideEffectDAO;

import java.util.List;

/**
 * Created by Maurya on 02/06/2015.
 */
public class HibernateSideEffectDAO implements SideEffectDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void deleteSideEffect(SideEffect sideEffect) {

    }

    @Override
    public List<SideEffect> getAllSideEffects() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(SideEffect.class);
        return c.list();
    }

    @Override
    public void saveSideEffect(SideEffect sideEffect) {

    }
}
