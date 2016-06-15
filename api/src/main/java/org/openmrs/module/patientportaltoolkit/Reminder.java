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

package org.openmrs.module.patientportaltoolkit;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.api.util.ToolkitResourceUtil;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Maurya on 08/06/2015.
 */
public class Reminder {

    private Integer id;
    private Patient patient;
    private Concept followProcedure;
    private String followProcedureName;
    private Date responseDate;
    private String responseComments;
    private User responseUser;
    private Date targetDate;
    private Date completeDate;
    private String doctorName;
    private Integer status; //0-not completed, 1-completed, -1-skipped, 2-scheduled
    private String responseType;
    private String responseAttributes;

    /**
     * Get response date in a given format
     *
     * @return formated response date
     */
    public String getResponseDateFormated() {
        return (responseDate==null? null : Context.getDateFormat().format(responseDate));
    }

    /**
     * Get target date in a given format
     *
     * @return formatted target date
     */
    public String getTargetDateFormated() {
        return (targetDate==null? null : Context.getDateFormat().format(targetDate));
    }



    /**
     * Sort by start_date
     *
     * @return date comparator
     */
    public static Comparator<Reminder> getDateComparator() {
        return new Comparator<Reminder>() {

            //in ascending order
            @Override
            public int compare(final Reminder g1, final Reminder g2) {
                return (g2.getTargetDate()==null||g1.getTargetDate()==null) ? 1 : g1.getTargetDate().compareTo(g2.getTargetDate());
            }
        };
    }

    /**
     * get reminder id
     *
     * @return id
     */
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

    public Concept getFollowProcedure() {
        return followProcedure;
    }

    public void setFollowProcedure(Concept followProcedure) {
        this.followProcedure = followProcedure;
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


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResponseUser(User responseUser) {
        this.responseUser = responseUser;
    }


    public Date getTargetDate() {
        return ToolkitResourceUtil.clearDate(targetDate);
    }


    public void setTargetDate(Date targetDate) {
        this.targetDate = ToolkitResourceUtil.clearDate(targetDate);
    }


    public Date getCompleteDate() {
        return ToolkitResourceUtil.clearDate(completeDate);
    }

    public void setCompleteDate(Date completeDate) {
       this.completeDate = ToolkitResourceUtil.clearDate(completeDate);
    }

    public String getDoctorName() {
        return doctorName;
    }


    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }


    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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


    public String getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(String responseAttributes) {
        this.responseAttributes = responseAttributes;
    }

}
