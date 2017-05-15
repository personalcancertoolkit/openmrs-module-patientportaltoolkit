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

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface GuidelineService extends OpenmrsService {

    @Transactional(readOnly = true)
    List<Guideline> getAllGuidlines();

    @Transactional(readOnly = true)
    List<Guideline> getGuidlinesByConditions(Set<Concept> conditions);

    @Transactional(readOnly = true)
    public List<Guideline>  findGuidelines(Patient pat);

    @Transactional(readOnly = true)
    GuidelineConditionSet getGuidlineConditionSetbyConditions(Set<Concept> conditions);
}
