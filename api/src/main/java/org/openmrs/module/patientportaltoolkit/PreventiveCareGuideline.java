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
    private Concept followupProcedure;
    private Set<PreventiveCareGuidelineInterval> pcgguidelineIntervalSet = new HashSet<PreventiveCareGuidelineInterval>(0);

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

    public Set<PreventiveCareGuidelineInterval> getPcgguidelineIntervalSet() {
        return pcgguidelineIntervalSet;
    }

    public void setPcgguidelineIntervalSet(Set<PreventiveCareGuidelineInterval> pcgguidelineIntervalSet) {
        this.pcgguidelineIntervalSet = pcgguidelineIntervalSet;
    }
}
