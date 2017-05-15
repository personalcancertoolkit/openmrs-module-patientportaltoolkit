/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.module.patientportaltoolkit.JournalEntry;

import java.util.List;

/**
 * Created by Maurya on 25/05/2015.
 */
public interface JournalEntryDAO {

    void deleteJournalEntry(JournalEntry entry);

    List<JournalEntry> getAllJournalEntries();

    JournalEntry getJournalEntry(String uuid);

    void saveJournalEntry(JournalEntry entry);

    List<JournalEntry> getJournalEntryForPerson(User user, Boolean orderByDateDesc);

    List<JournalEntry> findEntries(String searchText, Person p, Boolean orderByDateDesc);

    void softDelete(JournalEntry entry);

    List<JournalEntry> findComments(JournalEntry entry);
}
