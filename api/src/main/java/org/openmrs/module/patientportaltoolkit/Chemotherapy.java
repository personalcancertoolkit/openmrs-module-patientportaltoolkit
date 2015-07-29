package org.openmrs.module.patientportaltoolkit;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya on 07/07/2015.
 */
public class Chemotherapy {

    private String encounterUuid;

    private List<String> chemoMedications;

    private Date chemoStartDate;

    private Date chemoEndDate;

    private Boolean centralLine;

    private String pcpName;

    private String pcpEmail;

    private String pcpPhone;

    private String institutionName;

    private String institutionCity;

    private String institutionState;

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public List<String> getChemoMedications() {
        return chemoMedications;
    }

    public void setChemoMedications(List<String> chemoMedications) {
        this.chemoMedications = chemoMedications;
    }

    public Date getChemoStartDate() {
        return chemoStartDate;
    }

    public void setChemoStartDate(Date chemoStartDate) {
        this.chemoStartDate = chemoStartDate;
    }

    public Date getChemoEndDate() {
        return chemoEndDate;
    }

    public void setChemoEndDate(Date chemoEndDate) {
        this.chemoEndDate = chemoEndDate;
    }

    public Boolean getCentralLine() {
        return centralLine;
    }

    public void setCentralLine(Boolean centralLine) {
        this.centralLine = centralLine;
    }

    public String getPcpName() {
        return pcpName;
    }

    public void setPcpName(String pcpName) {
        this.pcpName = pcpName;
    }

    public String getPcpEmail() {
        return pcpEmail;
    }

    public void setPcpEmail(String pcpEmail) {
        this.pcpEmail = pcpEmail;
    }

    public String getPcpPhone() {
        return pcpPhone;
    }

    public void setPcpPhone(String pcpPhone) {
        this.pcpPhone = pcpPhone;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

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
}
