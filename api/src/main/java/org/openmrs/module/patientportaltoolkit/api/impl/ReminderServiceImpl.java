package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.Reminder;
import org.openmrs.module.patientportaltoolkit.api.ReminderService;
import org.openmrs.module.patientportaltoolkit.api.db.ReminderDAO;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public class ReminderServiceImpl extends BaseOpenmrsService implements ReminderService {

    protected ReminderDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public ReminderDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(ReminderDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<Reminder> getAllRemindersByPatient(Patient patient) {
        return dao.getAllRemindersByPatient(patient);
    }
}
