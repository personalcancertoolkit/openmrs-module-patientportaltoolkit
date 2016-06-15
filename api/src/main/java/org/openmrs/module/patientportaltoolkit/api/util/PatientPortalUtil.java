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

package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;

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
}
