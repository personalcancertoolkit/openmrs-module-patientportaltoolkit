#################
## 8.21.24
#################

-- openmrs.patientportal_patient_email_subscriptions definition

CREATE TABLE `patientportal_patient_email_subscriptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `broadcast_email` tinyint(1) NOT NULL DEFAULT '1',
  `appointment_reminder_email` tinyint(1) NOT NULL DEFAULT '1',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;

-- Populate the table with every existing person

INSERT INTO patientportal_patient_email_subscriptions (person_id)
SELECT p.person_id 
FROM person p  