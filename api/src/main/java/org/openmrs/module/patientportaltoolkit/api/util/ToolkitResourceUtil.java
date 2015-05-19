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

package org.openmrs.module.patientportaltoolkit.api.util;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAddress;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maurya.
 */
public class ToolkitResourceUtil {

    public static final String PHONE_NUMBER_ATTRIBUTE = "Telephone Number";
    public static final String EMAIL_ATTRIBUTE = "Email";

    public static Map<String, Object> generatePerson(Person person) {

        PersonAddress personAddress = new PersonAddress();

        //get the latest address
        for (PersonAddress pa : person.getAddresses()) {
            if (personAddress.isBlank())
                personAddress = pa;
            else if (pa.getDateCreated().after(personAddress.getDateCreated()))
                personAddress = pa;
        }
        Map<String, Object> personAddressObject = new HashMap<String, Object>();
        personAddressObject.put("id", personAddress.getUuid());
        personAddressObject.put("Address1", personAddress.getAddress1());
        personAddressObject.put("Address2", personAddress.getAddress2());
        personAddressObject.put("City/Village", personAddress.getCityVillage());
        personAddressObject.put("State/Province", personAddress.getStateProvince());
        personAddressObject.put("Country", personAddress.getCountry());
        personAddressObject.put("PostalCode", personAddress.getPostalCode());

        Map<String, Object> personObject = new HashMap<String, Object>();
        personObject.put("id", person.getUuid());
        personObject.put("GivenName", person.getGivenName());
        personObject.put("MiddleName", person.getMiddleName());
        personObject.put("FamilyName", person.getFamilyName());
        personObject.put("Age", person.getAge());

        personObject.put("DOB",new Date(person.getBirthdate().getTime())) ;
        personObject.put("Gender", person.getGender());
        personObject.put("Phone", person.getAttribute(PHONE_NUMBER_ATTRIBUTE).getValue());
        //personObject.put("Email", person.getAttribute(EMAIL_ATTRIBUTE).getValue());
        personObject.put("Address", personAddressObject);

        return personObject;
    }

    public static Map<String, Object> generatePatient(Patient patient) {

        Map<String, Object> patientObject=generatePerson(patient);

        return patientObject;
    }
}