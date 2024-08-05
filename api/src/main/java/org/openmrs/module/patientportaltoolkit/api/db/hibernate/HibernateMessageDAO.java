/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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

        @SuppressWarnings("unchecked")
        List<Message> list = c.list();
        return list;
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
        if (orderByDateDesc != null) {
            if (orderByDateDesc) {
                c.addOrder(Order.desc("dateCreated"));
            } else {
                c.addOrder(Order.asc("dateCreated"));
            }
        }
        Criterion sender = Restrictions.eq("sender", p);
        Criterion receiver = Restrictions.eq("receiver", p);
        LogicalExpression orExp = Restrictions.or(sender, receiver);
        c.add(orExp);
        c.add(Restrictions.eq("deleted", false));

        @SuppressWarnings("unchecked")
        List<Message> list = c.list();
        return list;
    }

    @Override
    public List<Message> getUnreadMessagesForPerson(Person p, Boolean orderByDateDesc) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Message.class);
        if (orderByDateDesc != null) {
            if (orderByDateDesc) {
                c.addOrder(Order.desc("dateCreated"));
            } else {
                c.addOrder(Order.asc("dateCreated"));
            }
        }

        c.add(Restrictions.eq("receiver", p));
        c.add(Restrictions.isNull("receiverViewedAt"));
        c.add(Restrictions.eq("deleted", false));

        @SuppressWarnings("unchecked")
        List<Message> list = c.list();
        return list;
    }

    @Override
    public List<Message> findMessage(String searchText, Person p, Boolean orderByDateDesc) {
        return null;
    }

    @Override
    public void softDelete(Message message) {
        if (message.getParentEntryId() == null) {
            List<Message> comments = findResponses(message);
            if (comments != null) {
                for (Message comment : comments) {
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

        @SuppressWarnings("unchecked")
        List<Message> list = c.list();
        return list;
    }
}
