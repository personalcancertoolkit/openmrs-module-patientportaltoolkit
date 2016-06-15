/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

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
