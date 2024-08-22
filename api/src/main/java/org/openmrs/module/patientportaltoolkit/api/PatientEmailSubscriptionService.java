/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Person;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientEmailSubscription;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface PatientEmailSubscriptionService extends OpenmrsService {

    @Transactional(readOnly = true)
    public PatientEmailSubscription getSubscriptionForPerson(Person person);

    @Transactional
    PatientEmailSubscription save(PatientEmailSubscription subscription);
}
