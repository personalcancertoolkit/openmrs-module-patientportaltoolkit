/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit;

import java.util.Date;

/**
 * Created by Maurya on 01/07/2015.
 */
public class GeneralHistory {

    private String encounterUuid;

    private String cancerType;

    private String cancerStage;

    private Date diagnosisDate;

    private String geneticOrPredisposingAbnormality;

    private Boolean hasGeneticOrPredisposingAbnormality;

    private String pcpName;

    private String pcpEmail;

    private String pcpPhone;

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public String getCancerType() {
        return cancerType;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getCancerStage() {
        return cancerStage;
    }

    public void setCancerStage(String cancerStage) {
        this.cancerStage = cancerStage;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getGeneticOrPredisposingAbnormality() {
        return geneticOrPredisposingAbnormality;
    }

    public void setGeneticOrPredisposingAbnormality(String geneticOrPredisposingAbnormality) {
        this.geneticOrPredisposingAbnormality = geneticOrPredisposingAbnormality;
    }

    public Boolean getHasGeneticOrPredisposingAbnormality() {
        return hasGeneticOrPredisposingAbnormality;
    }

    public void setHasGeneticOrPredisposingAbnormality(Boolean hasGeneticOrPredisposingAbnormality) {
        this.hasGeneticOrPredisposingAbnormality = hasGeneticOrPredisposingAbnormality;
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
}
