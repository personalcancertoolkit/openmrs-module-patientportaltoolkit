package org.openmrs.module.patientportaltoolkit;

import org.openmrs.Patient;

import java.util.Date;

/**
 * Created by maurya on 1/4/17.
 */
public class PatientPortalPersonAttributes {
    private Integer id;
    private Patient patient;
    private Date reminderTriggerDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getReminderTriggerDate() {
        return reminderTriggerDate;
    }

    public void setReminderTriggerDate(Date reminderTriggerDate) {
        this.reminderTriggerDate = reminderTriggerDate;
    }
}
