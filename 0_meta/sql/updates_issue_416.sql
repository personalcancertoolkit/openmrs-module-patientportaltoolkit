#################
## 5.23.23
#################

ALTER TABLE patientportal_pcg ADD COLUMN interval_description VARCHAR(256);

ALTER TABLE patientportal_guideline ADD COLUMN interval_description VARCHAR(256);

UPDATE patientportal_guideline SET interval_description = 'After 1 year and at 4 years' WHERE name = 'Colonoscopy';
UPDATE patientportal_guideline SET interval_description = 'Every 6 months' WHERE name = 'History and Physical';
UPDATE patientportal_guideline SET interval_description = 'Every 6 months' WHERE name = 'CEA Tests';
UPDATE patientportal_guideline SET interval_description = 'Once a year' WHERE name = 'CT Scan';
UPDATE patientportal_guideline SET interval_description = 'Once a year' WHERE name = 'CT Scan Pelvis';

UPDATE patientportal_pcg SET interval_description = 'Once a year' WHERE pcg_name = 'Influenza Vaccine';
UPDATE patientportal_pcg SET interval_description = 'At least once' WHERE pcg_name = 'Pneumococcal Vaccine';
UPDATE patientportal_pcg SET interval_description = 'Once a year' WHERE pcg_name = 'Blood Pressure Screening';
UPDATE patientportal_pcg SET interval_description = 'At least once' WHERE pcg_name = 'HIV Screening';
UPDATE patientportal_pcg SET interval_description = 'Every 2 years' WHERE pcg_name = 'Screening Mammography';
UPDATE patientportal_pcg SET interval_description = 'Every 3 years' WHERE pcg_name = 'Cervical Cancer Screening';
UPDATE patientportal_pcg SET interval_description = 'Every 5 years' WHERE pcg_name = 'Cholesterol Screening';