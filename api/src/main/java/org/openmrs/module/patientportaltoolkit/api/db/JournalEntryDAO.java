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
