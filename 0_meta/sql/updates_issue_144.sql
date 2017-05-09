#################
## 5.9.17 - UK
#################
## Used to avoid duplicating reminders from guidelines, as done with reminders
ALTER TABLE `patientportal_preventativecare_event` ADD `orig_target_date` DATETIME NULL DEFAULT NULL AFTER `target_date`;
## Drop not needed attributes
ALTER TABLE `patientportal_preventativecare_event`
  DROP `uuid`,
  DROP `doctor_name`,
  DROP `response_comments`;
## Add encounterUuid attribute
ALTER TABLE `patientportal_preventativecare_event` ADD `encounter_uuid` VARCHAR(38) NOT NULL AFTER `followup_procedure`;