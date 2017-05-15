/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
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
