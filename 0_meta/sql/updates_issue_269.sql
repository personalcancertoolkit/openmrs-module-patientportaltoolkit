#################
## 7.26.17 - UK
#################
## used to remove flex sigmoidoscopy 
DELETE FROM `patientportal_guideline_conceptmapping` WHERE `patientportal_guideline_conceptmapping`.`guideline_id` = 13;
DELETE FROM `patientportal_guideline_conceptmapping` WHERE `patientportal_guideline_conceptmapping`.`guideline_id` = 19;
DELETE FROM `patientportal_guideline_conceptmapping` WHERE `patientportal_guideline_conceptmapping`.`guideline_id` = 25;
DELETE FROM `patientportal_guideline_conceptmapping` WHERE `patientportal_guideline_conceptmapping`.`guideline_id` = 31;
DELETE FROM `patientportal_guideline_conditionsetmapping` WHERE `patientportal_guideline_conditionsetmapping`.`guideline_id` = 13;
DELETE FROM `patientportal_guideline_conditionsetmapping` WHERE `patientportal_guideline_conditionsetmapping`.`guideline_id` = 19;
DELETE FROM `patientportal_guideline_conditionsetmapping` WHERE `patientportal_guideline_conditionsetmapping`.`guideline_id` = 25;
DELETE FROM `patientportal_guideline_conditionsetmapping` WHERE `patientportal_guideline_conditionsetmapping`.`guideline_id` = 31;
DELETE FROM `patientportal_guideline` WHERE `patientportal_guideline`.`id` = 13;
DELETE FROM `patientportal_guideline` WHERE `patientportal_guideline`.`id` = 19;
DELETE FROM `patientportal_guideline` WHERE `patientportal_guideline`.`id` = 25;
DELETE FROM `patientportal_guideline` WHERE `patientportal_guideline`.`id` = 31;