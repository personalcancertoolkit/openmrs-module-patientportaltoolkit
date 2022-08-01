${ui.includeFragment("patientportaltoolkit", "headerForApp")}
<script>
    jq(document).ready(function () {
        let newPatientBirthDate = jq("#inputBD").datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function () {
            newPatientBirthDate.hide();
        }).data('datepicker');
        jq('#newPatientSave').click(
            async function () {
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
                        console.log("Received Identifier");
                    },
                    error: function (e) {
                        console.log('Error: ' + e);
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
                    error: function (e) {
                        console.log('Error: ' + e);
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
                    error: function (e) {
                        console.log('Error: ' + e);
                    },
                }).promise();
                await jq.ajax({
                    type: "GET",
                    url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/newPatientUser/"+personEmail,
                    success: function (response) {
                        console.log("Sent new account email");
                    },
                    error: function (e) {
                        console.log('Error: ' + e);
                    },
                }).promise();
                await jq.ajax({
                    type: "GET",
                    url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/createinitialpreferences/" + personUUID,
                    success: function (response) {
                        console.log("Added person Preferences");
                        jq(".patInput").val("");
                        jq("#success-div").show();
                    },
                    error: function (e) {
                        console.log('Error: ' + e);
                    },
                }).promise();
            });
    });
</script>

<body>
<div class="container bgcontent col-sm-offset-2">
    <div class="alert alert-success row" role="alert" style="display: none" id="success-div">
        Patient has been successfully created
    </div>
    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="inputFN">First Name</label>
            <input type="text" class="form-control patInput" id="inputFN" placeholder="First Name">
        </div>

        <div class="form-group col-md-6">
            <label for="inputLN">Last Name</label>
            <input type="text" class="form-control patInput" id="inputLN" placeholder="Last Name">
        </div>
    </div>

    <div class="form-row row">
        <div class="form-group col-md-6">
            <label for="inputBD">Birth Date</label>
            <input type="text" class="form-control patInput" id="inputBD" placeholder="mm/dd/yyyy">
        </div>

        <div class="form-group col-md-6">
            <label for="inputGender">Gender</label>
            <select class="form-control patInput" id="inputGender">
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
            <label for="patientEmail">Email address</label>
            <input type="email" class="form-control patInput" id="patientEmail" placeholder="Enter email">
        </div>

        <div class="form-group col-md-6">
            <label for="patientUserName">username</label>
            <input type="text" class="form-control patInput" id="patientUserName" placeholder="Enter username">
        </div>
    </div>


    <div class="form-row row">
        <div class="form-group col-md-6">
            <button id="newPatientSave" class="btn btn-primary">Save</button>
        </div>
    </div>
</div>
</body>