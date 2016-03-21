package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.module.patientportaltoolkit.SecurityLayer;

import java.util.List;

/**
 * Created by maurya on 3/21/16.
 */
public interface SecurityLayerDAO {

    SecurityLayer getSecurityLayerByUuid(String uuid);

    List<SecurityLayer> getAllSecurityLayers();
}
