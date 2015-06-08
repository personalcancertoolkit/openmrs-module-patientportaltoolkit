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

}
