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

package org.openmrs.module.patientportaltoolkit.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.api.MessageService;
import org.openmrs.module.patientportaltoolkit.api.db.MessageDAO;

import java.util.*;

/**
 * Created by maurya on 9/7/15.
 */
public class MessageServiceImpl extends BaseOpenmrsService implements MessageService {

    protected MessageDAO dao;

    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @return the dao
     */
    public MessageDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(MessageDAO dao) {
        this.dao = dao;
    }


    @Override
    public List<Message> getAllMessages() {
        return dao.getAllMessages();
    }

    @Override
    public Message getMessage(String uuid) {
        return dao.getMessage(uuid);
    }

    @Override
    public List<Message> getMessageForPerson(Person p, Boolean orderByDateDesc) {
        List<Message> totalMessageList= dao.getMessagesForPerson(p, orderByDateDesc);
        List<Message> returnMessageList = new ArrayList<Message>();
        for(Message je: totalMessageList){
            if(je.getParentEntryId() ==null)
                returnMessageList.add(je);
        }
        for(Message je:returnMessageList){
            if(je.getChildren() !=null){
                Set<Message> journalEntriesSet= new TreeSet<Message>(new Comparator<Message>() {
                    public int compare(Message je1, Message je2) {
                        return je1.getDateCreated().compareTo(je2.getDateCreated());
                    }
                });
                journalEntriesSet.addAll(je.getChildren());

                je.setChildren(journalEntriesSet);
            }
        }
        return returnMessageList;
    }

    @Override
    public List<Message> findMessage(String searchText, Person p, Boolean orderByDateDesc) {
        return null;
    }

    @Override
    public void saveMessage(Message message) throws APIException {
        Date date = new Date();
        message.setDateCreated(date);
        dao.saveMessage(message);
    }

    @Override
    public void deleteMessage(Message message) throws APIException {
        dao.deleteMessage(message);
    }

    @Override
    public void softDelete(Message message) {
        dao.softDelete(message);
    }

    @Override
    public List<Message> findResponses(Message message) {
        return dao.findResponses(message);
    }
}
