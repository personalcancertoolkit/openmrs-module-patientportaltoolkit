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

    /**
     * Response type
     */
    public static final String RESPONSE_COMPLETED = "Completed";
    /**
     * Response type
     */
    public static final String RESPONSE_SKIPPED = "Skipped";
    /**
     * Various flags used for calendar display
     */
    public static final String FLAG_NOT_PERFORMED_YES = "NOT PERFORMED: YES"; //removed its display from follow-up care calendar
    /**
     * Various flags used for calendar display
     */
    public static final String FLAG_NOT_PERFORMED_NO = "NOT PERFORMED: NO";
    /**
     * Various flags used for calendar display
     */
    public static String FLAG_COMPLETED = "COMPLETED";
    /**
     * Various flags used for calendar display
     */
    public static String FLAG_SCHEDULED = "SCHEDULED";
    /**
     * Various flags used for calendar display
     */
    public static String FLAG_SNOOZED = "SNOOZED"; //removed its entries from database
    /**
     * Various flags used for calendar display
     */
    public static String FLAG_ALERTED = "NEXT DUE";
    /**
     * Various flags used for calendar display
     */
    public static String FLAG_SKIPPED = "SKIPPED"; //removed its display from follow-up care calendar

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
    private Integer status; //0-not completed, 1-completed, -1-skipped
    private String responseType;
    private String flag;
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


    public String getFlag() {
        return flag;
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(String responseAttributes) {
        this.responseAttributes = responseAttributes;
    }

}
