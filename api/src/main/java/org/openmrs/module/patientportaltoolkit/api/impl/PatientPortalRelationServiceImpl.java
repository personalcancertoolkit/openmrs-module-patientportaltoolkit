package org.openmrs.module.patientportaltoolkit.api.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalRelationDAO;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

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
    public PatientPortalRelation getPatientPortalRelation(Patient requestedPatient, Person requestedPerson, User user) {
        return dao.getPatientPortalRelation(requestedPatient, requestedPerson, user);
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
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person) {
        return getPatientPortalRelationByPerson(person,false);
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person,Boolean includeRetired) {

        List<PatientPortalRelation> patientPortalRelations = dao.getPatientPortalRelationByPerson(person);
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
}
