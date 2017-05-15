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
