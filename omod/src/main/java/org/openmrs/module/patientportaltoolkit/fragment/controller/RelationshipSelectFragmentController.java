package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.RelationshipType;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 * Created by Maurya on 22/06/2015.
 */
public class RelationshipSelectFragmentController {
    public void controller(FragmentModel model,  @FragmentParam(value="selectedRelationShip", required=false) String relationshipType,@FragmentParam(value="parentForm", required=false) String parentForm) {
        model.addAttribute("relationshipTypes", Context.getPersonService().getAllRelationshipTypes());
        model.addAttribute("selectedRelationShiptype", relationshipType);
        model.addAttribute("parentForm", parentForm);
    }
}
