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
