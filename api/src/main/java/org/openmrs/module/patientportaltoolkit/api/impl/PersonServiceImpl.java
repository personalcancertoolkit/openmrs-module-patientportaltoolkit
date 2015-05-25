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

package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Person;
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
}
