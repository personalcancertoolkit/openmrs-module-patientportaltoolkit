package org.openmrs.module.patientportaltoolkit;

import org.openmrs.BaseOpenmrsObject;

/**
 * Created by maurya on 3/21/16.
 */
public class SecurityLayer extends BaseOpenmrsObject{
    private Integer id;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;


    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }



}
