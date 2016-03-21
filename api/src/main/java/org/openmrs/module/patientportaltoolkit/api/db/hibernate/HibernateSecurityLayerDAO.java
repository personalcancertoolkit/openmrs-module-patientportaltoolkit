package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.db.SecurityLayerDAO;

import java.util.List;

/**
 * Created by maurya on 3/21/16.
 */
public class HibernateSecurityLayerDAO implements SecurityLayerDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SecurityLayer getSecurityLayerByUuid(String uuid) {

        Criteria c = sessionFactory.getCurrentSession().createCriteria(SecurityLayer.class);
        c.add(Restrictions.eq("uuid", uuid));
        return (SecurityLayer)c.uniqueResult();
    }

    @Override
    public List<SecurityLayer> getAllSecurityLayers() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(SecurityLayer.class);
        return c.list();
    }
}
