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
 * Created by Maurya on 02/06/2015.
 */
public class SideEffect  {

    private Integer id;
    private String condition;
    private Set<Concept> concepts = new HashSet<Concept>(0);


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCondition() {
        return this.condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }
    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }
}
