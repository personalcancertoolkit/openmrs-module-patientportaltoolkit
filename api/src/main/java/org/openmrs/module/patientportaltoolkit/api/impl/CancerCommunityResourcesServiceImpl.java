package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.api.db.CancerCommunityResourcesDAO;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import java.util.*;

public class CancerCommunityResourcesServiceImpl extends BaseOpenmrsService implements CancerCommunityResourcesService {

    protected CancerCommunityResourcesDAO dao;
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public CancerCommunityResourcesDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(CancerCommunityResourcesDAO dao) {
        this.dao = dao;
    }

    public List<CancerCommunityResources> getCancerCommunityResourcesService() {
        return dao.getCancerCommunityResources();
    }

    public CancerCommunityResources getCancerCommunityResourceById(int id) {
        return dao.getCancerCommunityResourceById(id);
    }

    public void saveCancerCommunityResourcesService(CancerCommunityResources cancerCommuntiyResourcesData) throws APIException {
        dao.saveCancerCommunityResources(cancerCommuntiyResourcesData);
    }
}
