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

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalShare;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalRelationDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maurya.
 */
public class PatientPortalRelationServiceImpl extends BaseOpenmrsService implements PatientPortalRelationService {

    PatientPortalRelationDAO dao;

    public void setDao(PatientPortalRelationDAO dao) {
        this.dao = dao;
    }

    @Override
    public PatientPortalRelation getPatientPortalRelation(Person person, Person requestedPerson, User user) {
        return dao.getPatientPortalRelation(person, requestedPerson, user);
    }

    @Override
    public List<PatientPortalRelation> getAllPatientPortalRelations() {
        return dao.getAllPatientPortalRelation();
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient) {
        return getPatientPortalRelationByPatient(patient, false);
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient, Boolean includeRetired) {
        List<PatientPortalRelation> patientPortalRelations = dao.getPatientPortalRelationByPatient(patient);
        if(includeRetired)
            return patientPortalRelations;
        else
        {
            List<PatientPortalRelation> patientPortalNotRetiredRelations = new ArrayList<PatientPortalRelation>();
           for(PatientPortalRelation ppr: patientPortalRelations){
               if (!ppr.getRetired())
                   patientPortalNotRetiredRelations.add(ppr);
           }
           return patientPortalNotRetiredRelations;
        }
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByRelatedPerson(Person person) {
        return dao.getPatientPortalRelationByRelatedPerson(person);
    }

    @Override
    public List<PatientPortalRelation> getAcceptedPatientPortalRelationByRelatedPerson(Person person) {
        return dao.getAcceptedPatientPortalRelationByRelatedPerson(person);
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person) {
        return getPatientPortalRelationByPerson(person,false);
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person,Boolean includeRetired) {

        List<PatientPortalRelation> patientPortalRelations = new ArrayList<PatientPortalRelation>();
        if(dao.getPatientPortalRelationByPerson(person)!=null)
        patientPortalRelations.addAll(dao.getPatientPortalRelationByPerson(person));
        List<PatientPortalRelation> patientPortalRelationsByRelatedPerson = new ArrayList<PatientPortalRelation>();
        if(dao.getPatientPortalRelationByRelatedPerson(person)!=null)
        patientPortalRelationsByRelatedPerson.addAll(dao.getPatientPortalRelationByRelatedPerson(person));
        if(patientPortalRelationsByRelatedPerson!=null && !patientPortalRelationsByRelatedPerson.isEmpty())
        patientPortalRelations.addAll(patientPortalRelationsByRelatedPerson);
        if(includeRetired)
            return patientPortalRelations;
        else
        {
            List<PatientPortalRelation> patientPortalNotRetiredRelations = new ArrayList<PatientPortalRelation>();
            for(PatientPortalRelation ppr: patientPortalRelations){
                if (!ppr.getRetired())
                    patientPortalNotRetiredRelations.add(ppr);
            }
            return patientPortalNotRetiredRelations;
        }
    }

    @Override
    public PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation) {
        return dao.savePatientPortalRelation(patientPortalRelation);
    }

    @Override
    public PatientPortalRelation getPatientPortalRelation(String uuid) {
        return dao.getPatientPortalRelation(uuid);
    }

    @Override
    public void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation) {

    }

    @Override
    public void deletePatientPortalRelation(String uuid, User user) {
        PatientPortalRelation patientPortalRelation=getPatientPortalRelation(uuid);
        patientPortalRelation.setRetired(true);
        patientPortalRelation.setRetiredBy(user);
        patientPortalRelation.setDateRetired(new Date());
        dao.savePatientPortalRelation(patientPortalRelation);
    }

    @Override
    public void updatePatientPortalRelation(User user, Person person, String uuid) {

    }

    @Override
    public boolean hasAccessToShareType(Person person, Person relatedPerson, SecurityLayer shareType,User user) {
        //Check if both parties have an active relationship
        PatientPortalRelation pptRelation=getPatientPortalRelation(person,relatedPerson,user);
        if (pptRelation!=null) {
            if (!pptRelation.getRetired()) {
                return dao.hasShareType(person, relatedPerson, shareType);
            }
        }
        return false;
    }
    @Override
    public void saveShareTypes(Person personGrantingAccess, Person personGettingAccess, List<SecurityLayer> shareTypes) {
       List<PatientPortalShare> ppsharelist=dao.getAllAccess(personGettingAccess,personGrantingAccess);
       List<PatientPortalShare> ppsharelistDontRetire= new ArrayList<>();
       // List<PatientPortalShare> ppsharelistRetire= new ArrayList<>();
        PatientPortalShare pps=null;
        for (SecurityLayer sl:shareTypes) {

            pps=dao.getShareType(personGettingAccess,personGrantingAccess,sl);
            if (ppsharelist.contains(pps)){
                ppsharelistDontRetire.add(pps);
            }
            else{
                ppsharelistDontRetire.add(dao.saveShareType(new PatientPortalShare(personGettingAccess,personGrantingAccess,sl)));
            }

        }
        for (PatientPortalShare ppshare:ppsharelist) {
            if (!ppsharelistDontRetire.contains(ppshare)){
                ppshare.setRetired(true);
                dao.saveShareType(ppshare);
            }
        }
    }

}
