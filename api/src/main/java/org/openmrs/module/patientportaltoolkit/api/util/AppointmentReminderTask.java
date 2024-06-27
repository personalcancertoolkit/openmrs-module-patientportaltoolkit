package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.scheduler.tasks.AbstractTask;

public class AppointmentReminderTask extends AbstractTask {

    @Override
    public void execute() {
        AppointmentReminderUtil.run();
    }

}
