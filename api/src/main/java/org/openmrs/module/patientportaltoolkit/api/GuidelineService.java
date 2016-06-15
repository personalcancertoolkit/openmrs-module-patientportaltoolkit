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

package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
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
}
