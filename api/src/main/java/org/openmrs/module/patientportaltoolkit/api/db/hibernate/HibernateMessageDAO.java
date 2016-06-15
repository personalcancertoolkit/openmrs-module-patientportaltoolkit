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

package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.module.patientportaltoolkit.Message;
import org.openmrs.module.patientportaltoolkit.api.db.MessageDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by maurya on 9/7/15.
 */
public class HibernateMessageDAO implements MessageDAO {

    protected Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deleteMessage(Message message) {

    }

    @Override
    public List<Message> getAllMessages() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Message.class);
        return c.list();
    }

    @Override
    public Message getMessage(String uuid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Message.class);
        c.add(Restrictions.eq("uuid", uuid));
        return (Message) c.uniqueResult();
    }

    @Override
    public void saveMessage(Message message) {
        sessionFactory.getCurrentSession().saveOrUpdate(message);
    }

    @Override
    public List<Message> getMessagesForPerson(Person p, Boolean orderByDateDesc) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Message.class);
        if(orderByDateDesc != null){
            if(orderByDateDesc){
                c.addOrder(Order.desc("dateCreated"));
            }else{
                c.addOrder(Order.asc("dateCreated"));
            }
        }
        c.add(Restrictions.eq("sender",p));
        c.add(Restrictions.eq("deleted", false));
        return c.list();
    }

    @Override
    public List<Message> findMessage(String searchText, Person p, Boolean orderByDateDesc) {
        return null;
    }

    @Override
    public void softDelete(Message message) {
        if(message.getParentEntryId()==null) {
            List<Message> comments = findResponses(message);
            if(comments != null) {
                for(Message comment : comments) {
                    softDelete(comment);
                }
            }
        }

        message.setDeleted(true);
        message.setDateDeleted(new Date());
        saveMessage(message);

    }

    @Override
    public List<Message> findResponses(Message message) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Message.class);
        c.add(Restrictions.eq("deleted", false));
        c.add(Restrictions.eq("parentEntryId", message.getEntryId()));
        return c.list();
    }
}
