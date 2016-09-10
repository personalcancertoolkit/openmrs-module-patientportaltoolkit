package org.openmrs.module.patientportaltoolkit.web.rest.v1_0.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * Created by maurya on 9/9/16.
 */
@Resource(name = RestConstants.VERSION_1 + "/patientportaltoolkit/journalEntry", supportedClass = JournalEntry.class, supportedOpenmrsVersions = {
        "1.10.*", "1.11.*,1.12.*" })
public class JournalEntryResource extends DataDelegatingCrudResource<JournalEntry> {
    @Override
    public JournalEntry getByUniqueId(String uniqueId) {
        return Context.getService(JournalEntryService.class).getJournalEntry(uniqueId);
    }

    @Override
    protected void delete(JournalEntry journalEntry, String s, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public JournalEntry newDelegate() {
        return null;
    }

    @Override
    public JournalEntry save(JournalEntry journalEntry) {
        return null;
    }

    @Override
    public void purge(JournalEntry journalEntry, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        if (representation instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("title");
            description.addProperty("content");
            description.addProperty("children", Representation.DEFAULT);
            description.addProperty("dateCreated");
            description.addProperty("voided");
            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (representation instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("title");
            description.addProperty("content");
            description.addProperty("children", Representation.DEFAULT);
            description.addProperty("dateCreated");
            description.addProperty("voided");
            description.addSelfLink();
            return description;
        }
        return null;
    }
}
