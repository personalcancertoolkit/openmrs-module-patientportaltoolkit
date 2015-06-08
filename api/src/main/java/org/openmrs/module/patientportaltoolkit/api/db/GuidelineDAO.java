package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.module.patientportaltoolkit.Guideline;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface GuidelineDAO {


    public void deleteGuideline(Guideline guideline);

    public List<Guideline> getAllGuidelines();

    public void saveGuideline(Guideline guideline);
}
