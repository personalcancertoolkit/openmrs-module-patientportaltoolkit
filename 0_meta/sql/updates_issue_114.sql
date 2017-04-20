#################
## 4.11.17 - UK
#################
## Used for issue 165
ALTER TABLE `patientportal_reminder` ADD `orig_target_date` DATETIME NULL DEFAULT NULL AFTER `target_date`;
## Used to track changes when modifying "markCompleted" values
ALTER TABLE `patientportal_reminder` ADD `last_modified_date` DATETIME NULL DEFAULT NULL AFTER `complete_date`;