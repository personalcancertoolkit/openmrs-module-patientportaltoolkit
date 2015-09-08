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
