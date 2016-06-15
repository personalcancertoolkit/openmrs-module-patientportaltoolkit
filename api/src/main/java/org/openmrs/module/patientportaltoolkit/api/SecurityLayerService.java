/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

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
