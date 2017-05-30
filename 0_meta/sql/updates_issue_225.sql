# Enable null for encounter_uuid 
ALTER TABLE `patientportal_preventativecare_event` CHANGE `encounter_uuid` `encounter_uuid` VARCHAR(38) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;