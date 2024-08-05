#################
## 8.02.24
#################

ALTER TABLE openmrs.patientportal_messages ADD receiver_viewed_at datetime NULL;

/* SET the receiver_viewed_at to the current time so that any new messages will
have null timestamps */
UPDATE openmrs.patientportal_messages SET receiver_viewed_at = CURRENT_TIMESTAMP()