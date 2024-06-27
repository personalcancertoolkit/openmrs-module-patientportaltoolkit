/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Person;
import org.openmrs.api.context.Context;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maurya on 9/7/15.
 */
public class Message extends BaseOpenmrsObject {

    @SuppressWarnings("unused")
    private Message() {
    }

    private Integer entryId;
    private Integer parentEntryId;

    private String title;
    private String content;
    private Person sender;
    private Person receiver;
    private Date dateCreated;
    private boolean deleted;
    private Date dateDeleted;

    private Set<Message> children = new HashSet<Message>(0);

    public Message(String title, String content, Person sender, Person receiver) {
        this.title = title;
        this.content = content;
        this.dateCreated = new Date();
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * @see org.openmrs.OpenmrsObject#getId()
     */
    public Integer getId() {
        return getEntryId();
    }

    /**
     * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
     */
    public void setId(Integer id) {
        this.setEntryId(id);
    }

    /**
     * @param entryId the entryId to set
     */
    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    /**
     * @return the entryId
     */
    public Integer getEntryId() {
        return entryId;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param sender the creator to set
     */
    public void setSender(Person sender) {
        this.sender = sender;
    }

    /**
     * @return the sender
     */
    public Person getSender() {
        return sender;
    }

    /**
     * @param receiver the creator to set
     */
    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the receiver
     */
    public Person getReceiver() {
        return receiver;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedFormatedAsDateTime() {
        return (dateCreated == null ? null : Context.getDateTimeFormat().format(dateCreated));
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param dateDeleted the dateDeleted to set
     */
    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    /**
     * @return the dateDeleted
     */
    public Date getDateDeleted() {
        return dateDeleted;
    }

    /**
     * @return id of parent entry (null: original entry; not null: comment to an
     *         original/parent entry)
     */
    public Integer getParentEntryId() {
        return parentEntryId;
    }

    /**
     * @param parentEntryId id of parent entry
     */
    public void setParentEntryId(Integer parentEntryId) {
        this.parentEntryId = parentEntryId;
    }

    /**
     * @return children of parent entry (null: original entry; not null: comment to
     *         an original/parent entry)
     */
    public Set<Message> getChildren() {
        return children;
    }

    /**
     * @param children id of parent entry
     */
    public void setChildren(Set<Message> children) {
        this.children = children;
    }
}
