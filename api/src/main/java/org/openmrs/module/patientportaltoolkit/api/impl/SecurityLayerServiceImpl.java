/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.SecurityLayer;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.db.SecurityLayerDAO;

import java.util.List;

/**
 * Created by maurya on 3/21/16.
 */
public class SecurityLayerServiceImpl extends BaseOpenmrsService implements SecurityLayerService {
    protected SecurityLayerDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public SecurityLayerDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(SecurityLayerDAO dao) {
        this.dao = dao;
    }


    @Override
    public SecurityLayer getSecurityLayerByUuid(String uuid) {
        return dao.getSecurityLayerByUuid(uuid);
    }

    @Override
    public SecurityLayer getSecurityLayerByName(String name) {
        for(SecurityLayer sl:getAllSecurityLayers()){
           if(sl.getName().equals(name))
               return sl;
        }
        return null;
    }

    @Override
    public List<SecurityLayer> getAllSecurityLayers() {
        return dao.getAllSecurityLayers();
    }
}
