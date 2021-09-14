package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

import java.util.Date;

public class EventLog extends BaseOpenmrsObject {
    private Integer id;
    private String event;
    private String eventData;
    private User user;
    private Date createdAt;

    private EventLog(){}

    public EventLog(String event, User user, Date createdAt) {
        this.event = event;
        this.user = user;
        this.createdAt = createdAt;
    }

    public EventLog(String event, String eventData, User user, Date createdAt) {
        this.event = event;
        this.eventData = eventData;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
