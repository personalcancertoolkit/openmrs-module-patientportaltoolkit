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

/**
 * Created by maurya on 2/1/17.
 */
public class PreventiveCareGuidelineInterval {
    private Integer id;
    private PreventiveCareGuideline pcgguideline;
    private Integer intervalLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PreventiveCareGuideline getPcgguideline() {
        return pcgguideline;
    }

    public void setPcgguideline(PreventiveCareGuideline pcgguideline) {
        this.pcgguideline = pcgguideline;
    }

    public Integer getIntervalLength() {
        return intervalLength;
    }

    public void setIntervalLength(Integer intervalLength) {
        this.intervalLength = intervalLength;
    }
}
