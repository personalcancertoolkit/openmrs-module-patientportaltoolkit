package org.openmrs.module.patientportaltoolkit;

import org.openmrs.Patient;
import org.openmrs.User;

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
    private Date completeDate;
    private String doctorName;
    private Integer status; //0-not completed, 1-completed, -1-skipped, 2-scheduled

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

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
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
}
