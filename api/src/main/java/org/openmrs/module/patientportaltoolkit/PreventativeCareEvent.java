package org.openmrs.module.patientportaltoolkit;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

import java.util.Date;

/**
 * Created by maurya on 11/30/16.
 */
public class PreventativeCareEvent {

    private Integer id;
    private Patient patient;
    private Date responseDate;
    private String responseComments;
    private User responseUser;
    private Date targetDate;
    private Date origTargetDate;
    private String encounterUuid;
    private Date completeDate;
    private String doctorName;
    private Integer status; //0-not completed, 1-completed, -1-skipped, 2-scheduled
    private Concept followProcedure;
    private String followProcedureName;

    private PreventativeCareRule preventativeCareRule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseComments() {
        return responseComments;
    }

    public void setResponseComments(String responseComments) {
        this.responseComments = responseComments;
    }

    public User getResponseUser() {
        return responseUser;
    }

    public void setResponseUser(User responseUser) {
        this.responseUser = responseUser;
    }

   
    // Target Date
    public Date getTargetDate() {
        return ToolkitResourceUtil.clearDate(targetDate);
    }
    public void setTargetDate(Date targetDate) {
        this.targetDate = ToolkitResourceUtil.clearDate(targetDate);
    }
    

    // Orig Target Date
    public Date getOrigTargetDate() {
        return ToolkitResourceUtil.clearDate(origTargetDate);
    }
    public void setOrigTargetDate(Date origTargetDate) {
        this.origTargetDate = ToolkitResourceUtil.clearDate(origTargetDate);
    }
    

    // Completed Date
    public Date getCompleteDate() {
        return ToolkitResourceUtil.clearDate(completeDate);
    }
    public void setCompleteDate(Date completeDate) {
       this.completeDate = ToolkitResourceUtil.clearDate(completeDate);
    }
    

    // Completed Date
    public String getEncounterUuid() {
        return encounterUuid;
    }
    public void setEncounterUuid(String encounterUuid) {
       this.encounterUuid = encounterUuid;
    }
    
    

    
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PreventativeCareRule getPreventativeCareRule() {
        return preventativeCareRule;
    }

    public void setPreventativeCareRule(PreventativeCareRule preventativeCareRule) {
        this.preventativeCareRule = preventativeCareRule;
    }

    public Concept getFollowProcedure() {
        return followProcedure;
    }

    public void setFollowProcedure(Concept followProcedure) {
        this.followProcedure = followProcedure;
    }

    public String getFollowProcedureName() {
        if(followProcedureName == null && followProcedure != null) {
            followProcedureName = followProcedure.getName().getName();
        }
        return followProcedureName;
    }

    public void setFollowProcedureName(String followProcedureName) {
        this.followProcedureName = followProcedureName;
    }
}
