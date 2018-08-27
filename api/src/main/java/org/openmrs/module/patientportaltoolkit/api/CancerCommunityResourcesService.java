package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CancerCommunityResourcesService extends OpenmrsService {

    @Transactional(readOnly = true)
    List<CancerCommunityResources> getCancerCommunityResourcesService();

    @Transactional(readOnly = true)
    CancerCommunityResources getCancerCommunityResourceById(int id);

    @Transactional
    void saveCancerCommunityResourcesService(CancerCommunityResources cancerCommunityResourcesData);
}
