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
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.PatientPortalRelation;
import org.openmrs.module.patientportaltoolkit.api.db.PatientPortalRelationDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by Maurya.
 */
public class HibernatePatientPortalRelationDAO implements PatientPortalRelationDAO {

    protected final Log log = LogFactory.getLog(getClass());

    private SessionFactory sessionFactory;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PatientPortalRelation getPatientPortalRelation(String uuid) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("uuid", uuid));
        return (PatientPortalRelation) crit.uniqueResult();
    }

    @Override
    public PatientPortalRelation savePatientPortalRelation(PatientPortalRelation patientPortalRelation) {
        sessionFactory.getCurrentSession().saveOrUpdate(patientPortalRelation);
        return patientPortalRelation;
    }

    @Override
    public void deletePatientPortalRelation(PatientPortalRelation patientPortalRelation) {
        sessionFactory.getCurrentSession().delete(patientPortalRelation);
    }

    @Override
    public List<PatientPortalRelation> getAllPatientPortalRelation() {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.addOrder(Order.asc("person"));
        this.log.debug("HibernatePatientPortalRelationDAO:getAllPatientPortalRelation->" + " | token count=" + crit.list().size());
        return crit.list();
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPatient(Patient patient) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("patient", patient));
        crit.addOrder(Order.desc("dateCreated"));
        final List<PatientPortalRelation> list = crit.list();
        this.log.debug("HibernatePatientPortalSharingTokenDAO:getSharingTokenByPatient->" + patient + " | token count=" + list.size());
        if (list.size() >= 1) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByPerson(Person person) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("person", person));
        crit.addOrder(Order.desc("dateCreated"));
        final List<PatientPortalRelation> list = crit.list();
        this.log.debug("HibernatePatientPortalSharingTokenDAO:getSharingTokenByPerson->" + person + " | token count=" + list.size());
        if (list.size() >= 1) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<PatientPortalRelation> getPatientPortalRelationByRelatedPerson(Person person) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("relatedPerson", person));
        crit.addOrder(Order.desc("dateCreated"));
        final List<PatientPortalRelation> pptlist = crit.list();
        this.log.debug("HibernatePatientPortalSharingTokenDAO:getSharingTokenByPerson->" + person + " | token count=" + pptlist.size());
        if (pptlist.size() >= 1) {
            return pptlist;
        } else {
            return null;
        }
    }

    @Override
    public List<PatientPortalRelation> getAcceptedPatientPortalRelationByRelatedPerson(Person person) {
        final Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("relatedPerson", person));
        crit.add(Restrictions.eq("shareStatus", 1));
        crit.addOrder(Order.desc("dateCreated"));
        final List<PatientPortalRelation> pptlist = crit.list();
        this.log.debug("HibernatePatientPortalSharingTokenDAO:getSharingTokenByPerson->" + person + " | token count=" + pptlist.size());
        if (pptlist.size() >= 1) {
            return pptlist;
        } else {
            return null;
        }
    }

    @Override
    public PatientPortalRelation getPatientPortalRelation(Person person, Person requestedPerson, User requestingUser) {

        Person user = requestingUser.getPerson();

        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PatientPortalRelation.class);
        crit.add(Restrictions.eq("relatedPerson", requestedPerson));
        crit.add(Restrictions.eq("person", person));
        crit.addOrder(Order.desc("dateCreated"));

        List<PatientPortalRelation> list = crit.list();

        this.log.debug("HibernatePatientPortalSharingTokenDAO:getSharingToken->" + person + "|" + requestedPerson + "|"
                + requestingUser + "|token count=" + list.size());

        if (list.size() >= 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updatePatientPortalRelation(User user, Person person, String uuid) {
        final PatientPortalRelation patientPortalRelation = getPatientPortalRelation(uuid);
        if (patientPortalRelation != null) {
        final Date date = new Date();

        if (patientPortalRelation.getExpireDate().after(date)) {
            if (patientPortalRelation.getRelatedPerson() == null) {
                patientPortalRelation.setRelatedPerson(person);
                patientPortalRelation.setChangedBy(user);
                patientPortalRelation.setDateChanged(date);
                patientPortalRelation.setActivateDate(date);
                savePatientPortalRelation(patientPortalRelation);
                this.log.debug("Sharing token updated: " + patientPortalRelation.getId());
            } else {
                this.log.debug("Sharing token is igored because it was activated before by: " + patientPortalRelation.getChangedBy()
                        + " at " + patientPortalRelation.getActivateDate());
            }
        } else {
            this.log.debug("Sharing token is ignored because it expired at " + patientPortalRelation.getExpireDate());
        }
    } else {
        this.log.debug("Sharing token is ignored because it is invalid: " + uuid);
    }

    }
}
