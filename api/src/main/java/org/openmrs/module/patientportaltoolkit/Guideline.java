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
 * Created by Maurya on 07/06/2015.
 */
public class Guideline {

    private Integer id;
    private String name;
    private Concept followupProcedure;
    private Set<Concept> conditionsSet = new HashSet<Concept>(0);
    private String followupTimline;
    private Set<GuidelineInterval> guidelineIntervalSet = new HashSet<GuidelineInterval>(0);

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

    public Concept getFollowupProcedure() {
        return followupProcedure;
    }

    public void setFollowupProcedure(Concept followupProcedure) {
        this.followupProcedure = followupProcedure;
    }
    public Set<Concept> getConditionsSet() {
        return conditionsSet;
    }

    public void setConditionsSet(Set<Concept> conditionsSet) {
        this.conditionsSet = conditionsSet;
    }

    public String getFollowupTimline() {
        return followupTimline;
    }

    public void setFollowupTimline(String followupTimline) {
        this.followupTimline = followupTimline;
    }

    public Set<GuidelineInterval> getGuidelineIntervalSet() {
        return guidelineIntervalSet;
    }

    public void setGuidelineIntervalSet(Set<GuidelineInterval> guidelineIntervalSet) {
        this.guidelineIntervalSet = guidelineIntervalSet;
    }
}
