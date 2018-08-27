package org.openmrs.module.patientportaltoolkit;

public class CancerCommunityResources {

    private Integer id;
    private String cancerType;
    private String usefulContacts;
    private String resources;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }

    public String getCancerType() {  return cancerType;  }
    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getusefulContacts() {
        return usefulContacts;
    }
    public void setusefulContacts(String usefulContacts) {
        this.usefulContacts = usefulContacts;
    }

    public String getResources() {
        return resources;
    }
    public void setResources(String resources) {
        this.resources = resources;
    }
}
