package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;

import java.util.List;

public interface CancerCommunityResourcesDAO {

    List<CancerCommunityResources> getCancerCommunityResources();
    CancerCommunityResources getCancerCommunityResourceById(int cancerId);
    void saveCancerCommunityResources(CancerCommunityResources cancerCommunityResourcesData);
}
