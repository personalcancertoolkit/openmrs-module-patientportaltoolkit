${ui.includeFragment("patientportaltoolkit", "headerForApp")}
<script>
    jq(document).ready(function () {
        const birthdateInput = jq("#inputBD");
        let newPatientBirthDate = birthdateInput.datepicker({
            format: 'mm-dd-yyyy'
        }).on('changeDate', function () {
            newPatientBirthDate.hide();

        // date.toISOString() returns a string similar to "2023-04-19T18:02:34.550Z"
        // The OpenMRS' API endpoint that creates a new patient requires the date to 
        // look like "2023-04-19", so we split on the "T" and take the fist part
            birthdateInput.val(newPatientBirthDate.date.toISOString().split('T')[0])
            birthdateInput.trigger('focus');

        }).data('datepicker');

        // Also do this on blur because the changeDate event (above) is only emitted
        // when the datepicker's date is changed not necessarily when the input field 
        // changed
        birthdateInput.on('blur', function() {
            jq(this).val(newPatientBirthDate.date.toISOString().split('T')[0]);
        });


        jq('#addNewPatientForm').submit(async function(e) {

            e.preventDefault();
            e.stopPropagation();

            if (isNaN(Date.parse(birthdateInput.val()))) {
                alert("Please make sure the patient's birth date resembles this format: 1970-01-01");
                return;
            }

            const submissionStatusAlert = jq("#submissionStatusAlert");
            submissionStatusAlert.removeClass('alert-danger')
                .addClass("alert-warning")
                .html('Submitting ...')
                .show();
            
            let identifier = "";
            let personUUID = "";
            let personData = "";
            let userData = "";
            let personEmail=jq("#patientEmail").val();
            //get a new identifier

            await jq.ajax({
                type: "GET",
                dataType: 'json',
                url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/rest/v1/idgen/nextIdentifier?source=1",
                success: function (response) {
                    identifier = response.results[0].identifierValue;
                    personData = {
                        person: {
                            names: [{
                                givenName: jq("#inputFN").val(),
                                familyName: jq("#inputLN").val()
                            }],
                            gender: jq("#inputGender").val(),
                            birthdate: jq("#inputBD").val(),
                            attributes: [{
                                attributeType: "a38daf97-030b-4c3e-8d08-0f910cac0cd5",
                                value: personEmail
                            }],
                            addresses: [{
                                address1: jq("#patientAddress1").val(),
                                cityVillage: jq("#patientCity").val(),
                                country: jq("#patientCountry").val(),
                                postalCode: jq("#patientZipcode").val()
                            }]
                        },
                        "identifiers": [{
                            "identifierType": "05a29f94-c0ed-11e2-94be-8c13b969e334",
                            "identifier": identifier,
                            "location": "8d6c993e-c2cc-11de-8d13-0010c6dffd0f"
                        }]
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    const errorMessage = jqXHR.responseJSON.error.message;
                    console.error({errorMessage, errorThrown});
                    submissionStatusAlert.removeClass("alert-warning")
                        .addClass("alert-danger")
                        .html("Something went wrong. Check your internet connection");
                },
            }).promise();
            //add a new patient
            await jq.ajax({
                type: "POST",
                dataType: 'json',
                contentType: 'application/json',
                url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/rest/v1/patient",
                data: JSON.stringify(personData),
                success: function (response) {
                    const chars = "abcdefghijklmnopqrstuvwxyz!@#%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    const nums ="0123456789"
                    const passwordLength = 11;
                    let password = "";
                    for (let i = 0; i <= passwordLength; i++) {
                        let randomNumber = Math.floor(Math.random() * chars.length);
                        password += chars.substring(randomNumber, randomNumber +1);
                    }
                    let randomNumber = Math.floor(Math.random() * nums.length);
                    password += nums.substring(randomNumber, randomNumber +1);
                    personUUID = response.uuid;
                    userData = {
                        username: jq("#patientUserName").val(),
                        password: password,
                        person: personUUID,
                        roles: [
                            "d619b5ac-7304-4042-8de9-ac5c7219c027"
                        ]
                    }
                    console.log("Added Patient");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    const errorMessage = jqXHR.responseJSON.error.message;
                    console.error({errorMessage, errorThrown});
                    submissionStatusAlert.removeClass("alert-warning")
                        .addClass("alert-danger")
                        .html("Something went wrong. Check all the required fields for errors and try again");
                },
            }).promise();
            //add a new user
            await jq.ajax({
                type: "POST",
                dataType: 'json',
                contentType: 'application/json',
                url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/rest/v1/user",
                data: JSON.stringify(userData),
                success: function (response) {
                    console.log("Added User");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    const errorMessage = jqXHR.responseJSON.error.message;
                    if (errorMessage.includes('Username') && errorMessage.includes('is already in use')) {
                        submissionStatusAlert.removeClass("alert-warning")
                            .addClass("alert-danger")
                            .html('Username '+jq("#patientUserName").val() + ' is already in use. Please pick another');
                    } else {
                        submissionStatusAlert.removeClass("alert-warning")
                            .addClass("alert-danger")
                            .html("Check the username and try again");
                    }
                    console.error({errorMessage, errorThrown})
                },
            }).promise();
            await jq.ajax({
                type: "GET",
                url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/newPatientUser/"+personEmail,
                success: function (response) {
                    console.log("Sent new account email");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    const errorMessage = jqXHR.responseJSON.error.message;
                    console.error({errorMessage, errorThrown});
                    submissionStatusAlert.removeClass("alert-warning")
                        .addClass("alert-danger")
                        .html("Check the email and try again");
                },
            }).promise();
            await jq.ajax({
                type: "POST",
                url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/createinitialpreferences",
                data: {
                    personUUID: personUUID,
                    getsBroadcastEmails: jq("#newPatientBroadcastEmail").is(':checked'),
                    getsAppointmentReminderEmails: jq("#newPatientApprReminderEmail").is(':checked')
                },
                success: function (response) {
                    console.log("Added person Preferences");
                    jq(".patInput").val("");
                    jq("#success-div").show();
                    submissionStatusAlert.hide();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    const errorMessage = jqXHR.responseJSON.error.message;
                    console.error({errorMessage, errorThrown});
                },
            }).promise();

        });
    });
</script>

<body>
<div class="container bgcontent">
<form id="addNewPatientForm">
    <div class="alert alert-success" role="alert" style="display: none" id="success-div">
        Patient has been successfully created
    </div>
    <div class="alert alert-info" role="alert">
        Fields with asterisks (*) are required
    </div>
    <div id="submissionStatusAlert" class="alert" role="alert" style="display:none">
        Submitting ...
    </div>
    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="inputFN">First Name *</label>
            <input type="text" class="form-control patInput" id="inputFN" placeholder="First Name" required>
        </div>

        <div class="form-group col-md-6">
            <label for="inputLN">Last Name *</label>
            <input type="text" class="form-control patInput" id="inputLN" placeholder="Last Name" required>
        </div>
    </div>

    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="inputBD">Birth Date *</label>
            <input type="text" class="form-control patInput" id="inputBD" placeholder="mm-dd-yyyy" required>
        </div>

        <div class="form-group col-md-6">
            <label for="inputGender">Biological Sex *</label>
            <select class="form-control patInput" id="inputGender" required>
                <option value="M">Male</option>
                <option value="F">Female</option>
            </select>
        </div>
    </div>

    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="patientAddress1">Address</label>
            <input type="text" class="form-control patInput" id="patientAddress1" placeholder="Enter Address">
        </div>

        <div class="form-group col-md-6">
            <label for="patientCity">City</label>
            <input type="text" class="form-control patInput" id="patientCity" placeholder="Enter City">
        </div>
    </div>

    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="patientCountry">Country</label>
            <input type="text" class="form-control patInput" id="patientCountry" placeholder="Enter Country">
        </div>

        <div class="form-group col-md-6">
            <label for="patientZipcode">Zipcode</label>
            <input type="text" class="form-control patInput" id="patientZipcode" placeholder="Enter Zipcode">
        </div>
    </div>

    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="patientEmail">Email address *</label>
            <input type="email" class="form-control patInput" id="patientEmail" placeholder="Enter email" required>
        </div>

        <div class="form-group col-md-6">
            <label for="patientUserName">username *</label>
            <input type="text" class="form-control patInput" id="patientUserName" placeholder="Enter username" required>
        </div>
    </div>
    <div class="form-row row">
        <div class="form-group">
                <div class="col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" 
                                id="newPatientBroadcastEmail"
                                value="broadcastEmail" checked /> Gets Broadcast Emails
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"
                                id="newPatientApprReminderEmail"
                                value="appointmentReminderEmail"  checked /> Gets Appointment Reminder Emails
                        </label>
                    </div>
                </div>
            </div>
    </div>


    <div class="form-row row" style="margin-top:24px">
        <div class="form-group col-md-6">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
</form>
</div>
</body>