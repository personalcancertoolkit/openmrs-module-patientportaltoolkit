package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Person;

/**
 * Created by srikumma on 9/21/17.
 */
public class PatientPortalShare extends BaseOpenmrsMetadata {

    private Integer id;

    private Person person;

    private Person relatedPerson;

    private SecurityLayer shareType;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(Person relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public SecurityLayer getShareType() {
        return shareType;
    }

    public void setShareType(SecurityLayer shareType) {
        this.shareType = shareType;
    }
}
