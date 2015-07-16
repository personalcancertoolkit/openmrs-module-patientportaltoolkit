package org.openmrs.module.patientportaltoolkit;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 08/07/2015.
 */
public class Radiation {

    private List<String> radiationTypes;

    private Date startDate;

    private Date endDate;

    private String pcpName;

    private String pcpEmail;

    private String pcpPhone;

    private String institutionName;

    private String institutionCity;

    private String institutionState;

    public String getInstitutionCity() {
        return institutionCity;
    }

    public void setInstitutionCity(String institutionCity) {
        this.institutionCity = institutionCity;
    }

    public String getInstitutionState() {
        return institutionState;
    }

    public void setInstitutionState(String institutionState) {
        this.institutionState = institutionState;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getPcpPhone() {
        return pcpPhone;
    }

    public void setPcpPhone(String pcpPhone) {
        this.pcpPhone = pcpPhone;
    }

    public String getPcpEmail() {
        return pcpEmail;
    }

    public void setPcpEmail(String pcpEmail) {
        this.pcpEmail = pcpEmail;
    }

    public String getPcpName() {
        return pcpName;
    }

    public void setPcpName(String pcpName) {
        this.pcpName = pcpName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<String> getRadiationTypes() {
        return radiationTypes;
    }

    public void setRadiationTypes(List<String> radiationTypes) {
        this.radiationTypes = radiationTypes;
    }
}
