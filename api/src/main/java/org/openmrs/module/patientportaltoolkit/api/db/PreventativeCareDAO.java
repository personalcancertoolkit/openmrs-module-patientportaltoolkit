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
import org.openmrs.Patient;
import org.openmrs.module.patientportaltoolkit.PreventativeCareEvent;
import org.openmrs.module.patientportaltoolkit.PreventiveCareGuideline;

import java.util.Date;
import java.util.List;

/**
 * Created by maurya on 11/30/16.
 */
public interface PreventativeCareDAO {

    void deletePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    List<PreventativeCareEvent> getAllPreventativeCareEventsByPatient(Patient patient);

    PreventativeCareEvent savePreventativeCareEvent(PreventativeCareEvent preventativeCareEvent);

    PreventativeCareEvent getPreventativeCareEvent(Integer id);

    PreventativeCareEvent getPreventativeCareEventInStatusFarthestAwayFromDateForProcedureAndPatient(
            Patient patient,
            Date startDate,
            Integer numberOfMonthsIntoTheFuture,
            Concept followupProcedure,
            Integer status);

    PreventativeCareEvent getMostRecentPreventativeCareEventInStatusPriorToDateForProcedureAndPatient(
            Patient patient,
            Date startDate,
            Concept followupProcedure,
            Integer status);

    List<PreventiveCareGuideline> getAllPreventativeCareGuidelines();

    PreventiveCareGuideline getPreventativeCareGuidelinebyID(Integer id);
}
