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

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maurya on 2/1/17.
 */
public class PreventiveCareGuideline {
    private Integer id;
    private String name;
    private String intervalDescription;
    private Concept followupProcedure;
    private Set<PreventiveCareGuidelineInterval> pcgguidelineIntervalSet = new HashSet<PreventiveCareGuidelineInterval>(
            0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntervalDescription() {
        return intervalDescription;
    }

    public void setIntervalDescription(String intervalDescription) {
        this.intervalDescription = intervalDescription;
    }

    public Concept getFollowupProcedure() {
        return followupProcedure;
    }

    public void setFollowupProcedure(Concept followupProcedure) {
        this.followupProcedure = followupProcedure;
    }

    public Set<PreventiveCareGuidelineInterval> getPcgguidelineIntervalSet() {
        return pcgguidelineIntervalSet;
    }

    public void setPcgguidelineIntervalSet(Set<PreventiveCareGuidelineInterval> pcgguidelineIntervalSet) {
        this.pcgguidelineIntervalSet = pcgguidelineIntervalSet;
    }
}
