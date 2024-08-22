/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit;

import java.util.Date;

import org.openmrs.Person;

public class PatientEmailSubscription {

    private Integer id;
    private Person person;
    private boolean broadcastEmail;
    private boolean appointmentReminderEmail;
    private Date dateCreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean getBroadcastEmail() {
        return broadcastEmail;
    }

    public void setBroadcastEmail(boolean broadcastEmail) {
        this.broadcastEmail = broadcastEmail;
    }

    public boolean getAppointmentReminderEmail() {
        return appointmentReminderEmail;
    }

    public void setAppointmentReminderEmail(boolean appointmentReminderEmail) {
        this.appointmentReminderEmail = appointmentReminderEmail;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
