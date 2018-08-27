package org.openmrs.module.patientportaltoolkit.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.CancerCommunityResources;
import org.openmrs.module.patientportaltoolkit.api.CancerCommunityResourcesService;
import org.openmrs.module.patientportaltoolkit.api.util.PPTLogAppender;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageModel;
import java.util.List;

public class EditCancerCommunityResourcesFragmentController {

    protected final Log log = LogFactory.getLog(getClass());

    public void controller(FragmentModel model, PageRequest pageRequest) {
        log.info(PPTLogAppender.appendLog("REQUEST_EditCategorizeCommunitiesDataModal_FRAGMENT", pageRequest.getRequest()));
    }

    public void saveCancerCommunityResourcesData(FragmentModel model, @RequestParam(value = "cancerId", required = false) int cancerId,
                                                 @RequestParam(value = "cancerType", required = false) String cancerType,
                                   @RequestParam(value = "usefulContacts", required = false) String usefulContacts,
                                   @RequestParam(value = "resources", required = false) String cancerResources,
                                   HttpServletRequest servletRequestest) throws ParseException {

        log.info(PPTLogAppender.appendLog("NEW_SURGERY", servletRequestest,"Cancer Type:" , cancerType ,"Useful Contacts:", usefulContacts, "Cancer Resources:", cancerResources));

        CancerCommunityResources cancerCommunityResource = Context.getService(CancerCommunityResourcesService.class).getCancerCommunityResourceById(cancerId);
        cancerCommunityResource.setCancerType(cancerType);
        cancerCommunityResource.setusefulContacts(usefulContacts);
        cancerCommunityResource.setResources(cancerResources);

        Context.getService(CancerCommunityResourcesService.class).saveCancerCommunityResourcesService(cancerCommunityResource);
    }

}
