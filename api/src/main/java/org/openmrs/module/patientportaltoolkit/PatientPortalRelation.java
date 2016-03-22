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

    private Person person;

    private Person relatedPerson;

    private String relatedPersonName;

    private String relatedPersonEmail;

    private String relationType;

    private Date startDate;

    private Date activateDate;

    private Date expireDate;

    private SecurityLayer shareType;

    private static final String STATUS_ACCEPTED="Yes";
    private static final String STATUS_EXPIRED="Expired";
    private static final String STATUS_NOT_ACCEPTED_YET="Not yet";


    public PatientPortalRelation(Person person, Person relatedPerson) {
        super();
        this.person = person;
        this.relatedPerson = relatedPerson;
        this.startDate = new Date();
    }

    public PatientPortalRelation(Person person, Person relatedPersonperson, SecurityLayer shareType) {
        this(person,relatedPersonperson);
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
     * Get Patient
     *
     * @return a Patient
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Set Patient
     *
     * @param patient a Patient
     */
    public void setPerson(final Person person) {
        this.person = person;
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
    public SecurityLayer getShareType() {
        return this.shareType;
    }

    /**
     * Set sharing type
     *
     * @param shareType a sharing type
     */
    public void setShareType(final SecurityLayer shareType) {
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
