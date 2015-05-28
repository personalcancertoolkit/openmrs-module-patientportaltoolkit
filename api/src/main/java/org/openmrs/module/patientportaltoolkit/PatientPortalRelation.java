package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Patient;
import org.openmrs.Person;

import java.util.Date;

/**
 * Created by Maurya on 26/05/2015.
 */
public class PatientPortalRelation extends BaseOpenmrsMetadata implements Comparable<PatientPortalRelation> {

    private PatientPortalRelation(){}
    /** Unique identifying id */
    private Integer id;

    private String sharingToken;

    private Patient patient;

    private Person relatedPerson;

    private String relatedPersonName;

    private String relatedPersonEmail;

    private String relationType;

    private Date startDate;

    private Date activateDate;

    private Date expireDate;

    private String shareType;

    private static final String STATUS_ACCEPTED="Yes";
    private static final String STATUS_EXPIRED="Expired";
    private static final String STATUS_NOT_ACCEPTED_YET="Not yet";


    public PatientPortalRelation(Patient patient, Person person) {
        super();
        this.patient = patient;
        this.relatedPerson = person;
        this.startDate = new Date();
    }

    public PatientPortalRelation(Patient patient, Person person, String shareType) {
        this(patient,person);
        this.shareType=shareType;
    }
    @Override
    public int compareTo(PatientPortalRelation patientPortalRelation) {
        return patientPortalRelation.getId().compareTo(this.id);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Get sharing token
     *
     * @return a sharing token
     */
    public String getSharingToken() {
        return this.sharingToken;
    }

    /**
     * Set sharing token
     *
     * @param sharingToken a sharing token
     */
    public void setSharingToken(final String sharingToken) {
        this.sharingToken = sharingToken;
    }

    /**
     * Get Patient
     *
     * @return a Patient
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * Set Patient
     *
     * @param patient a Patient
     */
    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    /**
     * Get  a related Person
     *
     * @return a related Person
     */
    public Person getRelatedPerson() {
        return this.relatedPerson;
    }

    /**
     * Set a related Person
     *
     * @param relatedPerson a related person
     */
    public void setRelatedPerson(final Person relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    /**
     * Get related person name
     *
     * @return related person name
     */
    public String getRelatedPersonName() {
        return this.relatedPersonName;
    }

    /**
     * Set related person's name
     *
     * @param relatedPersonName name of the related person
     */
    public void setRelatedPersonName(final String relatedPersonName) {
        this.relatedPersonName = relatedPersonName;
    }

    /**
     * Get email of the related person
     *
     * @return email of the related person
     */
    public String getRelatedPersonEmail() {
        return this.relatedPersonEmail;
    }

    /**
     * Set email of the related person
     *
     * @param relatedPersonEmail email of the related person
     */
    public void setRelatedPersonEmail(final String relatedPersonEmail) {
        this.relatedPersonEmail = relatedPersonEmail;
    }

    /**
     * Get relation type
     *
     * @return a relation type as a string
     */
    public String getRelationType() {
        return this.relationType;
    }

    /**
     * Set relation type
     *
     * @param relationType a relation type
     */
    public void setRelationType(final String relationType) {
        this.relationType = relationType;
    }

    /**
     * Get start date of the sharing relation
     *
     * @return a start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Set start date
     *
     * @param startDate start date of the sharing relation
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get activation date
     *
     * @return the activation date of the sharing relation
     */
    public Date getActivateDate() {
        return this.activateDate;
    }

    /**
     * Set activation date
     *
     * @param activateDate activation date of a sharing relation
     */
    public void setActivateDate(final Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * Get expiration date of a sharing token
     *
     * @return the expiration of a sharing token
     */
    public Date getExpireDate() {
        return this.expireDate;
    }

    /**
     * Set expiration of a sharing relation
     *
     * @param expireDate expiration date of the relation
     */
    public void setExpireDate(final Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Get share type
     *
     * @return a sharing type
     */
    public String getShareType() {
        return this.shareType;
    }

    /**
     * Set sharing type
     *
     * @param shareType a sharing type
     */
    public void setShareType(final String shareType) {
        this.shareType = shareType;
    }

    /**
     * Get a status description on this sharing token
     *
     * @return status description
     */
    public String getStatus() {
        if(activateDate != null) {
            return STATUS_ACCEPTED;
        } else if(expireDate.before(new Date())) {
            return STATUS_EXPIRED;
        } else {
            return STATUS_NOT_ACCEPTED_YET;
        }
    }
}
