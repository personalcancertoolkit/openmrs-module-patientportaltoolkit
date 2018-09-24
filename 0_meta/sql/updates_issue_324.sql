use openmrs;

ALTER TABLE patientportal_pcg
ADD COLUMN cancerType_id INT NOT NULL;

update patientportal_pcg
Set cancerType_id = 1;
