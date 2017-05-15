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
