package org.openmrs.module.patientportaltoolkit.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maurya on 3/21/16.
 */
public interface SecurityLayerService extends OpenmrsService {
    @Transactional(readOnly = true)
    SecurityLayer getSecurityLayerByUuid(String uuid);

    @Transactional(readOnly = true)
    SecurityLayer getSecurityLayerByName(String name);

    @Transactional(readOnly = true)
    List<SecurityLayer> getAllSecurityLayers();
}
