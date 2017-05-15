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

import org.openmrs.Concept;

import java.util.Date;

/**
 * Created by maurya on 11/30/16.
 */
public class PreventativeCareRule {
    private Integer id;
    private Concept preventativeCareProcedure;
    private String preventativeCareProcedureName;
    private Integer interval; // in days
    private Date targetDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Concept getPreventativeCareProcedure() {
        return preventativeCareProcedure;
    }

    public void setPreventativeCareProcedure(Concept preventativeCareProcedure) {
        this.preventativeCareProcedure = preventativeCareProcedure;
    }

    public String getPreventativeCareProcedureName() {
        return preventativeCareProcedureName;
    }

    public void setPreventativeCareProcedureName(String preventativeCareProcedureName) {
        this.preventativeCareProcedureName = preventativeCareProcedureName;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }




}
