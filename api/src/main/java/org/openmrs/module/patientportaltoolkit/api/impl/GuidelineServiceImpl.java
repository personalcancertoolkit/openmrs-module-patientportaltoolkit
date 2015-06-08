package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.Guideline;
import org.openmrs.module.patientportaltoolkit.api.GuidelineService;
import org.openmrs.module.patientportaltoolkit.api.db.GuidelineDAO;

import java.util.List;
import java.util.Set;

/**
 * Created by Maurya on 08/06/2015.
 */
public class GuidelineServiceImpl extends BaseOpenmrsService implements GuidelineService {

    protected GuidelineDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public GuidelineDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(GuidelineDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<Guideline> getAllGuidlines() {
        return dao.getAllGuidelines();
    }

    @Override
    public List<Guideline> getGuidlinesByConditions(Set<Concept> conditions) {
        List<Guideline> allGuidelines=dao.getAllGuidelines();
        List<Guideline> conditionGuidelines = null;
        for(Guideline guideline:allGuidelines){
            if (guideline.getConditionsSet().equals(conditions))
                conditionGuidelines.add(guideline);
        }
        return conditionGuidelines;
    }

}
