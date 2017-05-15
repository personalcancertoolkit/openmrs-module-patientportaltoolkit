/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maurya on 3/7/16.
 */
public class PatientPortalUtil {

    public String formatDate(Date date){
        return new SimpleDateFormat(PatientPortalToolkitConstants.DATE_FORMAT_MONTHDATEYEAR).format(date);
    }

    public String formatDateWithSpecifiedFormat(Date date,String dateFormat){
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public PatientPortalRelation getRelationbetweenTwoPeople(Person person, Person requestedPerson){
        PatientPortalRelationService pprs = Context.getService(PatientPortalRelationService.class);
        PatientPortalRelation ppr=null;
              ppr = pprs.getPatientPortalRelation(person,requestedPerson,Context.getAuthenticatedUser());
        return ppr;
    }
}
