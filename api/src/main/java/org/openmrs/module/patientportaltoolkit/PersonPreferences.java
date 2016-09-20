package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Person;

/**
 * Created by maurya on 9/19/16.
 */
public class PersonPreferences extends BaseOpenmrsObject {

    private Integer id;

    private Person person;

    private Boolean myCancerBuddies;

    private String myCancerBuddiesName;

    private String myCancerBuddiesDescription;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Boolean getMyCancerBuddies() {
        return myCancerBuddies;
    }

    public void setMyCancerBuddies(Boolean myCancerBuddies) {
        this.myCancerBuddies = myCancerBuddies;
    }

    public String getMyCancerBuddiesName() {
        return myCancerBuddiesName;
    }

    public void setMyCancerBuddiesName(String myCancerBuddiesName) {
        this.myCancerBuddiesName = myCancerBuddiesName;
    }

    public String getMyCancerBuddiesDescription() {
        return myCancerBuddiesDescription;
    }

    public void setMyCancerBuddiesDescription(String myCancerBuddiesDescription) {
        this.myCancerBuddiesDescription = myCancerBuddiesDescription;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
