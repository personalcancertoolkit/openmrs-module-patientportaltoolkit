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
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.JournalEntry;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.PatientPortalToolkitConstants;
import org.openmrs.module.patientportaltoolkit.api.JournalEntryService;
import org.openmrs.module.patientportaltoolkit.api.PatientPortalRelationService;
import org.openmrs.module.patientportaltoolkit.api.SecurityLayerService;
import org.openmrs.module.patientportaltoolkit.api.db.JournalEntryDAO;

import java.util.*;

/**
 * Created by Maurya on 25/05/2015.
 */
public class JournalEntryServiceImpl extends BaseOpenmrsService implements JournalEntryService {

    protected JournalEntryDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public JournalEntryDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(JournalEntryDAO dao) {
        this.dao = dao;
    }

    /**
     *
     */
    public void deleteJournalEntry(JournalEntry entry) throws APIException {
        dao.deleteJournalEntry(entry);
    }

    /**
     *
     */
    public List<JournalEntry> getAllJournalEntries() {

        return dao.getAllJournalEntries();
    }

    /**
     *
     */
    public JournalEntry getJournalEntry(String uuid) {
        return dao.getJournalEntry(uuid);
    }

    /**
     *
     */
    public void saveJournalEntry(JournalEntry entry) throws APIException {
        entry.setCreator(Context.getAuthenticatedUser());
        Date date = new Date();
        entry.setDateCreated(date);
        dao.saveJournalEntry(entry);
    }

    /**
     *
     */
    public List<JournalEntry> getJournalEntryForPerson(User user, Boolean orderByDateDesc) {

        List<JournalEntry> totalJournalList = new ArrayList<JournalEntry>();

        if (dao.getJournalEntryForPerson(user, orderByDateDesc) != null)
            totalJournalList.addAll(dao.getJournalEntryForPerson(user, orderByDateDesc));

        List<PatientPortalRelation> pprlist = new ArrayList<PatientPortalRelation>();
        PatientPortalRelationService patientPortalRelationService = Context
                .getService(PatientPortalRelationService.class);
        SecurityLayerService securityLayerService = Context.getService(SecurityLayerService.class);

        Person personCheckingAccess = user.getPerson();
        Person personWhoMightHaveGivenAccess = null;

        if (patientPortalRelationService.getPatientPortalRelationByPerson(user.getPerson()) != null) {
            pprlist.addAll(Context.getService(PatientPortalRelationService.class)
                    .getPatientPortalRelationByPerson(user.getPerson()));

            for (PatientPortalRelation ppr : pprlist) {
                if (ppr.getShareStatus() == PatientPortalRelation.SHARE_STATUS_ACCEPTED) {

                    if (ppr.getPerson().equals(personCheckingAccess))
                        personWhoMightHaveGivenAccess = ppr.getRelatedPerson();
                    else
                        personWhoMightHaveGivenAccess = ppr.getPerson();

                    if (patientPortalRelationService.hasAccessToShareType(
                            personCheckingAccess,
                            personWhoMightHaveGivenAccess,
                            securityLayerService.getSecurityLayerByName(PatientPortalToolkitConstants.CAN_SEE_POSTS),
                            user)) {
                        totalJournalList.addAll(dao.getJournalEntryForPerson(
                                Context.getService(UserService.class)
                                        .getUsersByPerson(personWhoMightHaveGivenAccess, false).get(0),
                                orderByDateDesc));
                    }
                }
            }
        }

        Collections.sort(totalJournalList, new Comparator<JournalEntry>() {
            public int compare(JournalEntry j1, JournalEntry j2) {
                return j2.getDateCreated().compareTo(j1.getDateCreated());
            }
        });

        List<JournalEntry> returnJournalList = new ArrayList<JournalEntry>();
        for (JournalEntry je : totalJournalList) {
            if (je.getParentEntryId() == null)
                returnJournalList.add(je);
        }
        for (JournalEntry je : returnJournalList) {
            if (je.getChildren() != null) {
                Set<JournalEntry> journalEntriesSet = new TreeSet<JournalEntry>(new Comparator<JournalEntry>() {
                    public int compare(JournalEntry je1, JournalEntry je2) {
                        return je1.getDateCreated().compareTo(je2.getDateCreated());
                    }
                });
                for (JournalEntry jeChild : je.getChildren()) {
                    if (!jeChild.isDeleted()) {
                        journalEntriesSet.add(jeChild);
                    }
                }
                // journalEntriesSet.addAll(je.getChildren());

                je.setChildren(journalEntriesSet);
            }
        }
        return returnJournalList;
    }

    /**
     *
     */
    public List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc) {
        return dao.findEntries(searchText, p, orderByDateDesc);
    }

    public void softDelete(JournalEntry entry) {
        dao.softDelete(entry);
    }

    public List<JournalEntry> findComments(JournalEntry entry) {
        return dao.findComments(entry);
    }
}
