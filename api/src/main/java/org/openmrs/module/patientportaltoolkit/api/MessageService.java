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

import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.module.patientportaltoolkit.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maurya on 9/7/15.
 */
public interface MessageService {

    @Transactional(readOnly = true)
    List<Message> getAllMessages();

    /**
     * @param entryId
     * @return the journal entry with the corresponding entryId
     * @should return entry with given id
     */
    @Transactional(readOnly = true)
    Message getMessage(String uuid);

    /**
     * @param p the person who wrote the journal entries
     * @return all journal entries written by person p
     * @should return entries written by supplied person
     * @should return entries ordered by date
     */
    @Transactional(readOnly = true)
    List<Message> getMessageForPerson(Person p, Boolean orderByDateDesc);

    /**
     * @param p the person who wrote the journal entries
     * @param searchText the text to searc hfor
     * @return all journal entries written by person p
     * @should return entries written by supplied person
     * @should return entries ordered by date
     * @should return entries with searchString in title or body
     */
    @Transactional(readOnly = true)
    List<Message> findMessage(String searchText, Person p, Boolean orderByDateDesc);

    /**
     * Create or update journal entry
     */
    @Transactional
    void saveMessage(Message message) throws APIException;

    /**
     * Delete journal entry
     */
    @Transactional
    void deleteMessage(Message message) throws APIException;

    /**
     * Sets the deleted flag to true
     */
    @Transactional
    void softDelete(Message message);

    /**
     * find all comments of a given entry
     * @param entry parent entry of comments
     * @return all comments of a given entry
     */
    @Transactional
    List<Message> findResponses(Message message);
}
