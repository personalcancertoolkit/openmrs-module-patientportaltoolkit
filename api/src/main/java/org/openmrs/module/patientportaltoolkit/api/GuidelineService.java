package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 08/06/2015.
 */
public interface GuidelineService extends OpenmrsService {

    @Transactional(readOnly = true)
    List<Guideline> getAllGuidlines();

    @Transactional(readOnly = true)
    List<Guideline> getGuidlinesByConditions(Set<Concept> conditions);

    @Transactional(readOnly = true)
    public List<Guideline>  findGuidelines(Patient pat);
}
