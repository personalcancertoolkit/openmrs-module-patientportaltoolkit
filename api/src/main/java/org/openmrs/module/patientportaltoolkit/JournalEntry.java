package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Person;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maurya on 25/05/2015.
 */
public class JournalEntry extends BaseOpenmrsObject {

    private JournalEntry(){}

    private Integer entryId;
    private Integer parentEntryId;

    private String title;
    private String content;
    private Person creator;
    private Date dateCreated;
    private boolean deleted;
    private Date dateDeleted;

    private Set<JournalEntry> children = new HashSet<JournalEntry>(0);

    public JournalEntry(String title, String content) {
        super();
        this.title = title;
        this.content = content;
        this.dateCreated = new Date();
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
     * @param creator the creator to set
     */
    public void setCreator(Person creator) {
        this.creator = creator;
    }

    /**
     * @return the creator
     */
    public Person getCreator() {
        return creator;
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
     * @return id of parent entry (null: original entry; not null: comment to an original/parent entry)
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
     * @return children of parent entry (null: original entry; not null: comment to an original/parent entry)
     */
    public Set<JournalEntry> getChildren() {
        return children;
    }


    /**
     * @param children id of parent entry
     */
    public void setChildren(Set<JournalEntry> children) {
        this.children = children;
    }

}
