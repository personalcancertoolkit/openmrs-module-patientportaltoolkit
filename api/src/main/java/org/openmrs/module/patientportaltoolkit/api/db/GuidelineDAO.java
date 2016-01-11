package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Concept;
import org.openmrs.module.patientportaltoolkit.Guideline;

import java.util.List;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface GuidelineDAO {


    void deleteGuideline(Guideline guideline);

    List<Guideline> getAllGuidelines();

    void saveGuideline(Guideline guideline);

    List<Guideline> getGuidelinesbyConditions(List<Concept> conditions);
}
