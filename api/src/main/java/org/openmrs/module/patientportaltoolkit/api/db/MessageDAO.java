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
import org.openmrs.module.patientportaltoolkit.Message;

import java.util.List;

/**
 * Created by maurya on 9/7/15.
 */
public interface MessageDAO {

    void deleteMessage(Message message);

    List<Message> getAllMessages();

    Message getMessage(String uuid);

    void saveMessage(Message message);

    List<Message> getMessagesForPerson(Person p, Boolean orderByDateDesc);

    List<Message> findMessage(String searchText, Person p, Boolean orderByDateDesc);

    void softDelete(Message message);

    List<Message> findResponses(Message message);
}
