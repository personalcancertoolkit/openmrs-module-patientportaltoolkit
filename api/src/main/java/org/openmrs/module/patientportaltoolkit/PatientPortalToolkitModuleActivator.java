/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.patientportaltoolkit;


import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.ModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class PatientPortalToolkitModuleActivator extends BaseModuleActivator {
	
	protected Log log = LogFactory.getLog(getClass());
		

	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	@Override
	public void contextRefreshed() {
		super.contextRefreshed();    //To change body of overridden methods use File | Settings | File Templates.
		ensureRolesAreCreated();
	}

	private void ensureRolesAreCreated() {
		UserService userService = Context.getUserService();
		Role patientportalbasicrole = userService.getRole(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE_ROLE);
		if (patientportalbasicrole == null) {
			patientportalbasicrole = new Role();
			patientportalbasicrole.setRole(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE_ROLE);
			patientportalbasicrole.setDescription(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE_ROLE_DESCRIPTION);
			patientportalbasicrole.addPrivilege(userService.getPrivilege(PatientPortalToolkitConstants.APP_VIEW_PRIVILEGE));
			patientportalbasicrole.addPrivilege(userService.getPrivilege(PatientPortalToolkitConstants.VIEW_PROVIDER_PRIVILEGE));
			userService.saveRole(patientportalbasicrole);
		}
		userService.saveRole(patientportalbasicrole);
	}
}
