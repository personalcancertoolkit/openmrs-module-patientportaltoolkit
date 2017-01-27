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
