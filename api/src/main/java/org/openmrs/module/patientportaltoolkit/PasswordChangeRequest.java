package org.openmrs.module.patientportaltoolkit;


import org.openmrs.User;

import java.util.Date;

/**
 * Created by srikumma on 6/9/17.
 */
public class PasswordChangeRequest {
    private Integer id;
    private String uuid;
    private boolean retired;
    private User user;
    private Date dateTime;

    public PasswordChangeRequest(){
    }

    public PasswordChangeRequest(Date dateTime, String uuid, User user){
        this.dateTime=dateTime;
        this.uuid=uuid;
        this.user=user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
