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

package org.openmrs.module.patientportaltoolkit.api.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientportaltoolkit.*;
import org.openmrs.module.patientportaltoolkit.api.util.PatientPortalUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by maurya.
 */
public class ToolkitResourceUtil {

    public static final String PHONE_NUMBER_ATTRIBUTE = "Telephone Number";
    public static final String EMAIL_ATTRIBUTE = "Email";
    public static Integer i=0;
    public static PatientPortalUtil pptutil = new PatientPortalUtil();

    public static Date clearDate(Date dateTime) {
        if(dateTime == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateWithoutTime = cal.getTime();

        return dateWithoutTime;
    }
    public static Map<String, Object> generatePerson(Person person) {

        PersonAddress personAddress = new PersonAddress();

        //get the latest address
        for (PersonAddress pa : person.getAddresses()) {
            if (personAddress.isBlank())
                personAddress = pa;
            else if (pa.isPreferred())
                personAddress = pa;
        }
        Map<String, Object> personAddressObject = new HashMap<String, Object>();
        personAddressObject.put("id", personAddress.getUuid());
        personAddressObject.put("Address1", personAddress.getAddress1());
        personAddressObject.put("Address2", personAddress.getAddress2());
        personAddressObject.put("City/Village", personAddress.getCityVillage());
        personAddressObject.put("State/Province", personAddress.getStateProvince());
        personAddressObject.put("Country", personAddress.getCountry());
        personAddressObject.put("PostalCode", personAddress.getPostalCode());

        Map<String, Object> personObject = new HashMap<String, Object>();
        personObject.put("id", person.getUuid());
        personObject.put("GivenName", person.getGivenName());
        personObject.put("MiddleName", person.getMiddleName());
        personObject.put("FamilyName", person.getFamilyName());
        personObject.put("Age", person.getAge());

        if (person.getBirthdate() != null)
            personObject.put("DOB", new SimpleDateFormat().format(new Date(person.getBirthdate().getTime())));
        personObject.put("Gender", person.getGender());
        if (person.getAttribute(PHONE_NUMBER_ATTRIBUTE) != null)
            personObject.put("Phone", person.getAttribute(PHONE_NUMBER_ATTRIBUTE).getValue());
        if (person.getAttribute(EMAIL_ATTRIBUTE) != null)
            personObject.put("Email", person.getAttribute(EMAIL_ATTRIBUTE).getValue());
        personObject.put("Address", personAddressObject);

        return personObject;
    }

    public static Object updatePerson(String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            //convert JSON string to Map
            map = mapper.readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });

            System.out.println(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!map.containsKey("id") || map.get("id") == null)
            return null;
        String personId = map.get("id").toString();
        Person person = Context.getPersonService().getPersonByUuid(personId);

        if (map.get("GivenName") != null || map.get("MiddleName") != null || map.get("FamilyName") != null) {

            PersonName personName = new PersonName();
            if (map.get("GivenName") != null)
                personName.setGivenName(map.get("GivenName").toString());
            if (map.get("MiddleName") != null)
                personName.setMiddleName(map.get("MiddleName").toString());
            if (map.get("FamilyName") != null)
                personName.setFamilyName(map.get("FamilyName").toString());
            Set<PersonName> personNames = person.getNames();
            boolean personNameExists = false;

            for (PersonName pn : personNames) {
                if (pn.equalsContent(personName))
                    personNameExists = true;
            }
            if (!personNameExists) {
                for (PersonName pn : personNames) {
                    if (pn.getPreferred())
                        pn.setPreferred(false);
                }
                personName.setPreferred(true);
                personNames.add(personName);
            }

            person.setNames(personNames);
        }
        if (map.get("DOB") != null) {
            DateFormat df = new SimpleDateFormat();
            try {
                person.setBirthdate(df.parse(map.get("DOB").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (map.get("Gender") != null)
            person.setGender(map.get("Gender").toString());
        if (person.getAttribute(PHONE_NUMBER_ATTRIBUTE) != null) {
            if (map.get("Phone") != null)
                person.getAttribute("Telephone Number").setValue(map.get("Phone").toString());
        }
        if (person.getAttribute(PHONE_NUMBER_ATTRIBUTE) != null) {
            if (map.get("Email") != null)
                person.getAttribute("Email").setValue(map.get("Email").toString());

        }
        if (map.get("Address") != null) {
            Map<String, Object> addressMap = (Map<String, Object>) map.get("Address");
            PersonAddress personAddress = new PersonAddress();
            personAddress.setAddress1(addressMap.get("Address1").toString());
            personAddress.setAddress2(addressMap.get("Address2").toString());
            personAddress.setCityVillage(addressMap.get("City/Village").toString());
            personAddress.setStateProvince(addressMap.get("State/Province").toString());
            personAddress.setCountry(addressMap.get("Country").toString());
            personAddress.setPostalCode(addressMap.get("PostalCode").toString());
            boolean addressExists = false;
            Set<PersonAddress> personAddresses = person.getAddresses();

            for (PersonAddress pa : personAddresses) {
                if (pa.equalsContent(personAddress))
                    addressExists = true;
            }
            if (!addressExists)
                personAddresses.add(personAddress);

            person.setAddresses(personAddresses);
        }
        return generatePerson(Context.getPersonService().savePerson(person));

    }

    public static Patient updatePatient(String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            //convert JSON string to Map
            map = mapper.readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });

            System.out.println(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!map.containsKey("id") || map.get("id") == null)
            return null;
        String patientId = map.get("id").toString();
        Patient patient = Context.getPatientService().getPatientByUuid(patientId);

        if (map.get("GivenName") != null || map.get("MiddleName") != null || map.get("FamilyName") != null) {

            PersonName personName = new PersonName();
            if (map.get("GivenName") != null)
                personName.setGivenName(map.get("GivenName").toString());
            if (map.get("MiddleName") != null)
                personName.setMiddleName(map.get("MiddleName").toString());
            if (map.get("FamilyName") != null)
                personName.setFamilyName(map.get("FamilyName").toString());
            Set<PersonName> personNames = patient.getNames();
            boolean personNameExists = false;

            for (PersonName pn : personNames) {
                if (pn.equalsContent(personName))
                    personNameExists = true;
            }
            if (!personNameExists) {
                for (PersonName pn : personNames) {
                    if (pn.getPreferred())
                        pn.setPreferred(false);
                }
                personName.setPreferred(true);
                personNames.add(personName);
            }

            patient.setNames(personNames);
        }
        if (map.get("DOB") != null) {
            DateFormat df = new SimpleDateFormat();
            try {
                patient.setBirthdate(df.parse(map.get("DOB").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (map.get("Gender") != null)
            patient.setGender(map.get("Gender").toString());
        if (patient.getAttribute(PHONE_NUMBER_ATTRIBUTE) != null) {
            if (map.get("Phone") != null)
                patient.getAttribute("Telephone Number").setValue(map.get("Phone").toString());
        }
        if (patient.getAttribute(PHONE_NUMBER_ATTRIBUTE) != null) {
            if (map.get("Email") != null)
                patient.getAttribute("Email").setValue(map.get("Email").toString());

        }
        if (map.get("Address") != null) {
            Map<String, Object> addressMap = (Map<String, Object>) map.get("Address");
            PersonAddress personAddress = new PersonAddress();
            personAddress.setAddress1(addressMap.get("Address1").toString());
            personAddress.setAddress2(addressMap.get("Address2").toString());
            personAddress.setCityVillage(addressMap.get("City/Village").toString());
            personAddress.setStateProvince(addressMap.get("State/Province").toString());
            personAddress.setCountry(addressMap.get("Country").toString());
            personAddress.setPostalCode(addressMap.get("PostalCode").toString());
            boolean addressExists = false;
            Set<PersonAddress> personAddresses = patient.getAddresses();

            for (PersonAddress pa : personAddresses) {
                if (pa.equalsContent(personAddress))
                    addressExists = true;
            }
            if (!addressExists)
                personAddresses.add(personAddress);

            patient.setAddresses(personAddresses);
        }
        Context.getPatientService().savePatient(patient);
        return Context.getPatientService().getPatientByUuid(patient.getUuid());
    }

    public static Object generateJournals(List<JournalEntry> journalEntries) {

        List<Object> journalEntriesMap = new ArrayList<Object>();
        for (JournalEntry journalEntry : journalEntries) {
            journalEntriesMap.add(generateJournal(journalEntry));
        }
        return journalEntriesMap;
    }

    public static Object generateJournal(JournalEntry journalEntry) {


        Map<String, Object> personMap = generatePerson(journalEntry.getCreator().getPerson());

        Map<String, Object> journalEntryMap = new HashMap<String, Object>();
        journalEntryMap.put("id", journalEntry.getUuid());
        journalEntryMap.put("title", journalEntry.getTitle());
        journalEntryMap.put("content", journalEntry.getContent());
        journalEntryMap.put("date", new SimpleDateFormat().format(new Date(journalEntry.getDateCreated().getTime())));
        journalEntryMap.put("creator", personMap);

        return journalEntryMap;
    }

    public static JournalEntry transformJournal(String json) {
        String title = null;
        String content = null;
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            //convert JSON string to Map
            map = mapper.readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });

            System.out.println(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map.get("title") != null)
            title = map.get("title").toString();
        if (map.get("content") != null)
            content = map.get("content").toString();

        if (content != null && title != null)
            return new JournalEntry(title, content);

        return null;

    }

    public static Object generateRelations(List<PatientPortalRelation> patientPortalRelations) {

        List<Object> patientRelationsMap = new ArrayList<Object>();
        for (PatientPortalRelation patientRelation : patientPortalRelations) {
            patientRelationsMap.add(generateRelation(patientRelation));
        }
        return patientRelationsMap;
    }

    public static Object generateRelation(PatientPortalRelation patientPortalRelation) {


        Map<String, Object> relatedPersonMap = generatePerson(patientPortalRelation.getRelatedPerson());
        Map<String, Object> creatorPersonMap = generatePerson(patientPortalRelation.getCreator().getPerson());
        Map<String, Object> patientPersonMap = generatePerson(patientPortalRelation.getPerson());
        Map<String, Object> relationshipTypeMap = (Map<String, Object>) generateRelationType(patientPortalRelation.getRelationType());

        Map<String, Object> patientRelationMap = new HashMap<String, Object>();
        patientRelationMap.put("id", patientPortalRelation.getUuid());
        patientRelationMap.put("relationType", relationshipTypeMap);
        patientRelationMap.put("shareType", patientPortalRelation.getShareTypeA());
        patientRelationMap.put("dateStarted", new SimpleDateFormat().format(new Date(patientPortalRelation.getStartDate().getTime())));
        patientRelationMap.put("patient", patientPersonMap);
        patientRelationMap.put("creator", creatorPersonMap);
        patientRelationMap.put("relatedPerson", relatedPersonMap);
       // patientRelationMap.put("relatedPerson", relatedPersonMap);

        return patientRelationMap;
    }

    public static Object generateRelationType(RelationshipType relationshipType) {



        Map<String, Object> relationTypeMap = new HashMap<String, Object>();
        relationTypeMap.put("id", relationshipType.getRelationshipTypeId());
        relationTypeMap.put("aIsToB", relationshipType.getaIsToB());
        relationTypeMap.put("bIsToA", relationshipType.getbIsToA());

        // patientRelationMap.put("relatedPerson", relatedPersonMap);

        return relationTypeMap;
    }



    public static Object generateEncounterTypes(List<EncounterType> encounterTypes) {

        List<Object> encounterTypesMap = new ArrayList<Object>();
        for (EncounterType encounterType : encounterTypes) {
            encounterTypesMap.add(generateEncounterType(encounterType));
        }
        return encounterTypesMap;
    }

    public static Object generateEncounterType(EncounterType encounterType) {

        Map<String, Object> creatorPersonMap = generatePerson(encounterType.getCreator().getPerson());

        Map<String, Object> encounterTypeMap = new HashMap<String, Object>();
        encounterTypeMap.put("id", encounterType.getUuid());
        encounterTypeMap.put("name", encounterType.getName());
        encounterTypeMap.put("description", encounterType.getDescription());
        encounterTypeMap.put("dateCreated", new SimpleDateFormat().format(new Date(encounterType.getDateCreated().getTime())));
        encounterTypeMap.put("creator", creatorPersonMap);

        return encounterTypeMap;
    }

    public static Object generatePatientPortalForms(List<PatientPortalForm> patientPortalForms) {

        List<Object> patientPortalFormsMap = new ArrayList<Object>();
        for (PatientPortalForm patientPortalForm : patientPortalForms) {
            patientPortalFormsMap.add(generatePatientPortalForm(patientPortalForm));
        }
        return patientPortalFormsMap;
    }

    public static Object generatePatientPortalForm(PatientPortalForm patientPortalForm) {


        Map<String, Object> patientPortalFormMap = new HashMap<String, Object>();
        patientPortalFormMap.put("id", patientPortalForm.getUuid());
        patientPortalFormMap.put("name", patientPortalForm.getName());
        patientPortalFormMap.put("description", patientPortalForm.getDescription());
        if (!patientPortalForm.getConcepts().isEmpty()) {
            List<Concept> questionConcepts = new ArrayList<Concept>();
            for (Concept concept : patientPortalForm.getConcepts()) {
                questionConcepts.add(concept);
            }
            patientPortalFormMap.put("concepts", generateConcepts(questionConcepts));
        }
        return patientPortalFormMap;
    }

    public static Object generateSideEffects(List<SideEffect> sideEffects) {

        List<Object> sideEffectsMap = new ArrayList<Object>();
        for (SideEffect sideEffect : sideEffects) {
            sideEffectsMap.add(generateSideEffect(sideEffect));
        }
        return sideEffectsMap;
    }

    public static Object generateSideEffect(SideEffect sideEffect) {


        Map<String, Object> sideEffectMap = new HashMap<String, Object>();
        sideEffectMap.put("id", sideEffect.getId());
        sideEffectMap.put("condition", sideEffect.getCondition());
        if (!sideEffect.getConcepts().isEmpty()) {
            List<Concept> sideEffectConcepts = new ArrayList<Concept>();
            for (Concept concept : sideEffect.getConcepts()) {
                sideEffectConcepts.add(concept);
            }
            sideEffectMap.put("concepts", generateConcepts(sideEffectConcepts));
        }
        return sideEffectMap;
    }
    public static Object generateConcepts(List<Concept> concepts) {

        List<Object> conceptsMap = new ArrayList<Object>();
        for (Concept concept : concepts) {
            conceptsMap.add(generateConcept(concept));
        }
        return conceptsMap;
    }

    public static Object generateConcept (Concept concept) {

        Map<String, Object> conceptMap = generateBasicConcept(concept);
        if (concept.isSet()) {
            List<Object> conceptSetsMap = new ArrayList<Object>();
            for (Concept conceptSet : concept.getSetMembers()) {
                conceptSetsMap.add(generateBasicConcept(conceptSet));
            }
            conceptMap.put("conceptSets",conceptSetsMap) ;
        }

        if (concept.getAnswers()!= null) {
            List<Object> conceptAnswerMap = new ArrayList<Object>();
            for (ConceptAnswer conceptAnswer : concept.getAnswers()) {
                conceptAnswerMap.add(generateBasicConcept(conceptAnswer.getAnswerConcept()));
            }
            conceptMap.put("conceptAnswers",conceptAnswerMap) ;
        }
        return conceptMap;
    }

    public static Map<String, Object> generateBasicConcept (Concept concept) {

        Map<String, Object> creatorPersonMap = generatePerson(concept.getCreator().getPerson());

        Map<String, Object> basicConceptMap = new HashMap<String, Object>();
        basicConceptMap.put("id", concept.getUuid());
        if(concept.getName()!=null)
            basicConceptMap.put("name", concept.getName().getName());
        else
            basicConceptMap.put("name", null);
        if(concept.getDescription()!=null)
            basicConceptMap.put("description", concept.getDescription().getDescription());
        else
            basicConceptMap.put("description", null);
        basicConceptMap.put("creator", creatorPersonMap);

        return basicConceptMap;
    }

    public static Object generateObservations(List<Obs> observations) {

        List<Object> observationsMap = new ArrayList<Object>();
        for (Obs observation : observations) {
            observationsMap.add(generateObservation(observation));
        }
        return observationsMap;
    }

    public static Object generateObservation(Obs observation) {


        Map<String, Object> observationMap = new HashMap<String, Object>();
        observationMap.put("id", observation.getUuid());
        observationMap.put("concept", generateConcept(observation.getConcept()));
        ConceptDatatype conceptDatatype=observation.getConcept().getDatatype();

        if(conceptDatatype.isBoolean()) {
            observationMap.put("type", "boolean");
            observationMap.put("value", observation.getValueBoolean());
        }
        else if(conceptDatatype.isText()) {
            observationMap.put("type", "text");
            observationMap.put("value", observation.getValueText());
        }
        else if(conceptDatatype.isDateTime()) {
            observationMap.put("type", "datetime");
            observationMap.put("value", new SimpleDateFormat().format(new Date(observation.getValueDatetime().getTime())));
        }
        else if(conceptDatatype.isCoded()) {
            observationMap.put("type", "coded");
            observationMap.put("value", generateConcept(observation.getValueCoded()));
        }
        return observationMap;
    }

    public static Object generateEncounters(List<Encounter> encounters) {

        List<Object> encountersMap = new ArrayList<Object>();
        for (Encounter encounter : encounters) {
            encountersMap.add(generateEncounter(encounter));
        }
        return encountersMap;
    }

    public static Object generateEncounter(Encounter encounter) {


        Map<String, Object> encounterMap = new HashMap<String, Object>();
        encounterMap.put("id", encounter.getUuid());
        List<Obs> obsevationList=new ArrayList<Obs>();
        obsevationList.addAll(encounter.getAllObs());
        encounterMap.put("observations",generateObservations(obsevationList));
        encounterMap.put("datecreated",new SimpleDateFormat().format(new Date(encounter.getDateCreated().getTime())));
        return encounterMap;
    }

    public static Object generateGuidelines(List<Guideline> guidelines) {

        List<Object> guidelinesMap = new ArrayList<Object>();
        for (Guideline guideline : guidelines) {
            guidelinesMap.add(generateGuideline(guideline));
        }
        return guidelinesMap;
    }

    public static Object generateGuideline(Guideline guideline) {


        Map<String, Object> guidelineMap = new HashMap<String, Object>();
        List<Concept> conditionsList=new ArrayList<Concept>();
        conditionsList.addAll(guideline.getConditionsSet());
        guidelineMap.put("id", guideline.getId());
        guidelineMap.put("name",guideline.getName());
        guidelineMap.put("followupTimeline",guideline.getFollowupTimline());
        guidelineMap.put("followupProcedure",generateConcept(guideline.getFollowupProcedure()));
        guidelineMap.put("conditions",generateConcepts(conditionsList));
        return guidelineMap;
    }

    public static Object generateReminders(List<Reminder> reminders) {

        List<Object> remindersMap = new ArrayList<Object>();
        Integer fake_id = -1;
        for (Reminder reminder : reminders) {
            fake_id=fake_id+1; // only used if reminder does not have a real id
            remindersMap.add(generateReminder(reminder, fake_id));
        }
        return remindersMap;
    }

    public static Object generateReminder(Reminder reminder, Integer fake_id) {
        Map<String, Object> reminderMap = new HashMap<String, Object>();

        //reminderMap.put("patient",generatePerson(reminder.getPatient()));
        reminderMap.put("followProcedure",generateConcept(reminder.getFollowProcedure()));
        reminderMap.put("followProcedureName",reminder.getFollowProcedureName());
        reminderMap.put("targetDate",reminder.getTargetDate());
        reminderMap.put("formatedTargetDate", pptutil.formatDate(reminder.getTargetDate()));
        //details for calendar
        if(reminder.getId()==null){ 
            reminderMap.put("id", "X" + Integer.toString(fake_id)); // X ensures that we never have a 'fake' ID with the same value as a real ID
        } else {
            reminderMap.put("id", reminder.getId());
        }
        reminderMap.put("startDate",reminder.getTargetDate());
        reminderMap.put("endDate",reminder.getTargetDate());
        reminderMap.put("name",reminder.getFollowProcedureName());
        reminderMap.put("concept_id",reminder.getFollowProcedure().getConceptId());
        //System.out.println("Here i am - util file");
        if(reminder.getCompleteDate() != null){
            reminderMap.put("formatedCompletedDate", pptutil.formatDate(reminder.getCompleteDate()));
            reminderMap.put("completedDate", reminder.getCompleteDate());
            reminderMap.put("doctorName", reminder.getDoctorName());
            reminderMap.put("comments", reminder.getResponseComments());
        }
        reminderMap.put("status",reminder.getStatus());
        
        ////
        switch (reminder.getFollowProcedure().getConceptId()) {
            //Colonoscopy
            case 162900: reminderMap.put("color", "brown");
                break;
            //H & E
            case 162901 : reminderMap.put("color", "red");
                break;
            //CEA Tests
            case 162902 : reminderMap.put("color", "blue");
                break;
            //CT Scans
            case 162903 : reminderMap.put("color", "green");
                break;
            //CT Scan Pelvis
            case 162904 : reminderMap.put("color", "purple");
                break;
            //Flex Sigmoidoscopy
            case 162905 : reminderMap.put("color", "orange");
                break;
        }
        //reminderMap.put("status",reminder.getStatus());
        return reminderMap;
    }

    public static Object generatePreventiveCareEvents(List<PreventativeCareEvent> pcgEvents) throws ParseException {

        List<Object> pcgEventsMap = new ArrayList<Object>();
        for (PreventativeCareEvent pcgEvent : pcgEvents) {
            i=i+1;
            pcgEventsMap.add(generatePreventiveCareEvent(pcgEvent));
        }
        return pcgEventsMap;
    }

    public static Object generatePreventiveCareEvent(PreventativeCareEvent pcgevent) throws ParseException {


        Map<String, Object> preventiveCareMap = new HashMap<String, Object>();

        List<Object> influenzaVaccines = new ArrayList<>();
        influenzaVaccines = GenerateTreatmentClassesUtil.generateInfluenzaVaccines(pcgevent.getPatient());
        List<Object> pneumococcalVaccines = new ArrayList<>();
        pneumococcalVaccines = GenerateTreatmentClassesUtil.generatePneumococcalVaccines(pcgevent.getPatient());
        List<Object> bPScreenings = new ArrayList<>();
        bPScreenings = GenerateTreatmentClassesUtil.generateBPScreenings(pcgevent.getPatient());
        List<Object> cholesterolScreenings = new ArrayList<>();
        cholesterolScreenings = GenerateTreatmentClassesUtil.generateCholesterolScreenings(pcgevent.getPatient());
        List<Object> hivScreenings = new ArrayList<>();
        hivScreenings = GenerateTreatmentClassesUtil.generateHivScreenings(pcgevent.getPatient());
        List<Object> mammographyScreenings = new ArrayList<>();
        mammographyScreenings = GenerateTreatmentClassesUtil.generateMammographyScreenings(pcgevent.getPatient());
        List<Object> cervicalCancerScreenings = new ArrayList<>();
        cervicalCancerScreenings = GenerateTreatmentClassesUtil.generateCervicalCancerScreenings(pcgevent.getPatient());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        preventiveCareMap.put("followProcedure",generateConcept(pcgevent.getFollowProcedure()));
        preventiveCareMap.put("followProcedureName",pcgevent.getFollowProcedureName());
        
        
        // Enforce that, if event is influenza, the date falls between oct and march
        Date targetDate = pcgevent.getTargetDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        int month = cal.get(Calendar.MONTH);
        if (pcgevent.getFollowProcedure().getConceptId() == 162938){ // If this event is Influenza Vaccine
            if(!(month >= (10-1) || month <= (3-1))){ // Ensure that date is between oct and march. Note, minus 1 due to jan = 0
                //System.out.println("chaning date!");
                cal.set(Calendar.MONTH, Calendar.OCTOBER);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                //System.out.println("Changing date to...");
                targetDate = cal.getTime();
                //System.out.println(targetDate);
            }
        }
        //Calendar currentDate = Calendar.getInstance();
        Date currentDate = new Date();
        Calendar minDate = Calendar.getInstance();
        minDate.setTime(targetDate);
        minDate.add(Calendar.DATE, -90);
        Calendar maxDate = Calendar.getInstance();
        maxDate.setTime(targetDate);
        maxDate.add(Calendar.DATE, 90);
        Calendar completedDateCal = Calendar.getInstance();



      //  if(cal.after(minDate) && cal.before(maxDate))
      //      System.out.println("\nFullDate");
        preventiveCareMap.put("targetDate", targetDate);
        if(pcgevent.getId()==null)
            preventiveCareMap.put("id", i);
        else
            preventiveCareMap.put("id", pcgevent.getId());
        preventiveCareMap.put("startDate",targetDate);
        preventiveCareMap.put("endDate",targetDate);
        preventiveCareMap.put("name",pcgevent.getFollowProcedureName());

        switch (pcgevent.getFollowProcedure().getConceptId()) {
            //Influenza Vaccine
            case 162938:
                preventiveCareMap.put("color", "brown");
                if (!influenzaVaccines.isEmpty()) {
                    for (Object influenzaVac : influenzaVaccines) {
                        HashMap<String, String> map = (HashMap<String, String>) influenzaVac;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                } else
                    preventiveCareMap.put("location", "Due Next");

                break;
            //Pneumococcal Vaccine
            case 162939:
                preventiveCareMap.put("color", "red");
                if (!pneumococcalVaccines.isEmpty()) {
                    for (Object pneumococcalVaccine : pneumococcalVaccines) {
                        HashMap<String, String> map = (HashMap<String, String>) pneumococcalVaccine;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
        }
                else
                    preventiveCareMap.put("location", "Due Next");

                break;
            //Blood Pressure Screening
            case 162941 : preventiveCareMap.put("color", "blue");
                if(!bPScreenings.isEmpty()) {
                    for (Object bPScreening : bPScreenings) {
                        HashMap<String, String> map = (HashMap<String, String>) bPScreening;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                }
                else
                    preventiveCareMap.put("location", "Due Next");
                break;
            //HIV Screening
            case 162942 : preventiveCareMap.put("color", "green");
                if(!hivScreenings.isEmpty()) {
                    for (Object hivScreening : hivScreenings) {
                        HashMap<String, String> map = (HashMap<String, String>) hivScreening;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                }
                else
                    preventiveCareMap.put("location", "Due Next");
                break;
            //Screening Mammography
            case 162943 : preventiveCareMap.put("color", "purple");
                if(!mammographyScreenings.isEmpty()) {
                    for (Object mammographyScreening : mammographyScreenings) {
                        HashMap<String, String> map = (HashMap<String, String>) mammographyScreening;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                }
                else
                    preventiveCareMap.put("location", "Due Next");
                break;
            //Cervical Cancer Screening
            case 162944 : preventiveCareMap.put("color", "orange");
                if(!cervicalCancerScreenings.isEmpty()) {
                    for (Object cervicalCancerScreening : cervicalCancerScreenings) {
                        HashMap<String, String> map = (HashMap<String, String>) cervicalCancerScreening;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                }
                else
                    preventiveCareMap.put("location", "Due Next");
                break;
            //Cholesterol Screening
            case 162940 : preventiveCareMap.put("color", "black");
                if(!cholesterolScreenings.isEmpty()) {
                    for (Object cholesterolScreening : cholesterolScreenings) {
                        HashMap<String, String> map = (HashMap<String, String>) cholesterolScreening;
                        Date completedDate = formatter.parse(map.get("completedDate"));
                        completedDateCal.setTime(completedDate);
                        if (completedDateCal.after(minDate) && completedDateCal.before(maxDate)) {
                            preventiveCareMap.put("startDate", completedDate);
                            preventiveCareMap.put("endDate", completedDate);
                            preventiveCareMap.put("location", "Completed");
                        }
                    }
                }
                else
                    preventiveCareMap.put("location", "Due Next");
                break;
        }
        return preventiveCareMap;
    }

    public static Date getFirstSurgeryDate(Patient patient) {
        Date firstSurgeryDate = null;
        List<Surgery> surgeryEncounters = new ArrayList<>();
        surgeryEncounters = GenerateTreatmentClassesUtil.generateSurgeries(patient);
        if (!surgeryEncounters.isEmpty()) {
            for (Surgery surgery : surgeryEncounters) {
                if (firstSurgeryDate == null)
                    firstSurgeryDate = surgery.getSurgeryDate();
                else {
                    if (firstSurgeryDate.after(surgery.getSurgeryDate()))
                        firstSurgeryDate = surgery.getSurgeryDate();
                }
            }
        }
        return firstSurgeryDate;
    }

}
