package org.openmrs.module.patientportaltoolkit.api.impl;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalRelationDAO;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

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
        return dao.getPatientPortalRelationByPatient(patient);
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person) {
        return null;
    }

    @Override
    public PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation) {
        return null;
    }

    @Override
    public PatientPortalRelation getPatientPortalRelation(String uuid) {
        return null;
    }

    @Override
    public void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation) {

    }

    @Override
    public void deletePatientPortalRelation(String uuid) {

    }

    @Override
    public void updatePatientPortalRelation(User user, Person person, String uuid) {

    }
}
