package org.openmrs.module.patientportaltoolkit;

/**
 * Created by maurya on 2/1/17.
 */
public class PreventiveCareGuidelineInterval {
    private Integer id;
    private PreventiveCareGuideline pcgguideline;
    private Integer intervalNumber;
    private Integer intervalLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PreventiveCareGuideline getPcgguideline() {
        return pcgguideline;
    }

    public void setPcgguideline(PreventiveCareGuideline pcgguideline) {
        this.pcgguideline = pcgguideline;
    }

    public Integer getIntervalNumber() {
        return intervalNumber;
    }

    public void setIntervalNumber(Integer intervalNumber) {
        this.intervalNumber = intervalNumber;
    }

    public Integer getIntervalLength() {
        return intervalLength;
    }

    public void setIntervalLength(Integer intervalLength) {
        this.intervalLength = intervalLength;
    }
}
