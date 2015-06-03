package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.SideEffect;
import org.openmrs.module.patientportaltoolkit.api.SideEffectService;
import org.openmrs.module.patientportaltoolkit.api.db.SideEffectDAO;

import java.util.List;

/**
 * Created by Maurya on 02/06/2015.
 */
public class SideEffectServiceImpl extends BaseOpenmrsService implements SideEffectService {

    protected SideEffectDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public SideEffectDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(SideEffectDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<SideEffect> getAllSideEffects() {
        return dao.getAllSideEffects();
    }

    @Override
    public List<Concept> getAllSideEffectsForPatient(Patient patient) {
        return null;
    }
}
