/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.openmrs.module.patientportaltoolkit.api.PatientEmailSubscriptionService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientEmailSubscriptionDAO;

public class PatientEmailSubscriptionServiceImpl extends BaseOpenmrsService implements PatientEmailSubscriptionService {

    protected PatientEmailSubscriptionDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public PatientEmailSubscriptionDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(PatientEmailSubscriptionDAO dao) {
        this.dao = dao;
    }

    @Override
    public PatientEmailSubscription getSubscriptionForPerson(Person person) {
        return dao.getSubscriptionForPerson(person);
    }

    @Override
    public PatientEmailSubscription save(PatientEmailSubscription subscription) {
        return dao.save(subscription);
    }

}
