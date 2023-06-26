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
import org.openmrs.Concept;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
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

        @SuppressWarnings("unchecked")
        List<Guideline> list = c.list();
        return list;
    }

    @Override
    public void saveGuideline(Guideline guideline) {

    }

    @Override
    public List<Guideline> getGuidelinesbyConditions(List<Concept> conditions) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Guideline.class);
        @SuppressWarnings("unchecked")
        List<Guideline> list = c.list();
        return list;
    }

    @Override
    public List<GuidelineConditionSet> getAllGuidelineConditionSet() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(GuidelineConditionSet.class);
        @SuppressWarnings("unchecked")
        List<GuidelineConditionSet> list = c.list();
        return list;
    }
}
