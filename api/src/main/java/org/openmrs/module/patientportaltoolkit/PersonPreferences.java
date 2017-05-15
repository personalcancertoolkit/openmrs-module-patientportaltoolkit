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

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Person;

/**
 * Created by maurya on 9/19/16.
 */
public class PersonPreferences extends BaseOpenmrsObject {

    private Integer id;

    private Person person;

    private Boolean myCancerBuddies;

    private String myCancerBuddiesName;

    private String myCancerBuddiesDescription;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Boolean getMyCancerBuddies() {
        return myCancerBuddies;
    }

    public void setMyCancerBuddies(Boolean myCancerBuddies) {
        this.myCancerBuddies = myCancerBuddies;
    }

    public String getMyCancerBuddiesName() {
        return myCancerBuddiesName;
    }

    public void setMyCancerBuddiesName(String myCancerBuddiesName) {
        this.myCancerBuddiesName = myCancerBuddiesName;
    }

    public String getMyCancerBuddiesDescription() {
        return myCancerBuddiesDescription;
    }

    public void setMyCancerBuddiesDescription(String myCancerBuddiesDescription) {
        this.myCancerBuddiesDescription = myCancerBuddiesDescription;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
