/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Concept;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.GuidelineConditionSet;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface GuidelineDAO {


    void deleteGuideline(Guideline guideline);

    List<Guideline> getAllGuidelines();

    void saveGuideline(Guideline guideline);

    List<Guideline> getGuidelinesbyConditions(List<Concept> conditions);

    List<GuidelineConditionSet> getAllGuidelineConditionSet();
}
