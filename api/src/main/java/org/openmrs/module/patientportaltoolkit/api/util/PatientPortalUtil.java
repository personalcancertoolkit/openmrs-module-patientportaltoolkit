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
