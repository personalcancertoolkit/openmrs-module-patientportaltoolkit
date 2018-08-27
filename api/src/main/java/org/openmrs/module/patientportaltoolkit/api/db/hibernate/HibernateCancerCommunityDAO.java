package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.PatientPortalForm;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.db.CancerCommunityResourcesDAO;

import java.util.List;

public class HibernateCancerCommunityDAO implements CancerCommunityResourcesDAO {

    protected Log log = LogFactory.getLog(getClass());
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<CancerCommunityResources> getCancerCommunityResources() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(CancerCommunityResources.class);
        return c.list();
    }

    public CancerCommunityResources getCancerCommunityResourceById(int cancerId) {

        Criteria c = sessionFactory.getCurrentSession().createCriteria(CancerCommunityResources.class);
        c.add(Restrictions.eq("id", cancerId));
        return (CancerCommunityResources) c.uniqueResult();
}

    @Override
    public void saveCancerCommunityResources(CancerCommunityResources cancerCommunityResourcesData) {
        sessionFactory.getCurrentSession().saveOrUpdate(cancerCommunityResourcesData);
    }
}
