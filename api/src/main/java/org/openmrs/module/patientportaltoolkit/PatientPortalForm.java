package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maurya on 01/06/2015.
 */
public class PatientPortalForm extends BaseOpenmrsMetadata {

    /** Unique identifying id */
    private Integer id;

    private String name;

    private String description;

    private Set<Concept> concepts = new HashSet<Concept>(0);

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }
    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }
}
