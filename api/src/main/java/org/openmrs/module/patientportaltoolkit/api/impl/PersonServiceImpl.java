/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.api.PersonService;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

import java.util.Map;

/**
 * Created by maurya.
 */
public class PersonServiceImpl extends BaseOpenmrsService implements PersonService {


    @Override
    public Map<String, Object> getPerson(String personId) {
        Person person =  Context.getPersonService().getPersonByUuid(personId);
        if (person != null)
            return ToolkitResourceUtil.generatePerson(person);

        return null;
    }

    @Override
    public Object updatePerson(String personJson) {
        return ToolkitResourceUtil.updatePerson(personJson);
    }

    @Override
    public User getUserByEmail(String email) {
        org.openmrs.api.PersonService ps = Context.getPersonService();
        ps.getPersonAttributeTypeByName("Email");


        return null;
    }
}
