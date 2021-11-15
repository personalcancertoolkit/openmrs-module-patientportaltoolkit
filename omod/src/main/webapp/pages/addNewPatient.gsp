${ui.includeFragment("patientportaltoolkit", "headerForApp")}
<style>
.hidden {
    display: none;
}

</style>
<script>
    jq(document).ready(function () {
        let newPatientBirthDate = jq("#inputBD").datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function () {
            newPatientBirthDate.hide();
        }).data('datepicker');
        jq('#newPatientSave').click(
            async function () {
                let identifier = "";
                let personUUID = "";
                let personData = "";
                let userData = "";
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
                                attributes: [{
                                    attributeType: "a38daf97-030b-4c3e-8d08-0f910cac0cd5",
                                    value: jq("#personEmail").val()
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
                        personUUID = response.uuid;
                        userData = {
                            username: jq("#patientUserName").val(),
                            password: jq("#patientPassword").val(),
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
                await saveGenHistory(personUUID).promise();
            });
        jq('#newGenHistoryCancerabnormalitySelect').change(function () {
            if ("yes" == jq('#newGenHistoryCancerabnormalitySelect').find(":selected").text().toLowerCase()) {
                jq('#canAbnTypeDiv').show();
            } else {
                jq('#canAbnTypeDiv').hide();
            }
        });

        function saveGenHistory(personUUID) {
           // alert(personUUID + "---" + jq("#genHistoryCancerTypeSelect").val() + "---" + jq("#genHistoryCancerStageSelect").val() + "---" + jq("#genHistoryDate").val() + "---" + jq("#newGenHistoryCancerabnormalitySelect").val() + "---" + jq("#genHistoryCancerabnormalityTypeSelect").val() + "---" + jq("#genHistoryCancerPcpName").val() + "---" + jq("#genHistoryCancerPcpEmail").val() + "---" + jq("#genHistoryCancerPcpPhone").val());
            if ("yes" == jq('#newGenHistoryCancerabnormalitySelect').find(":selected").text().toLowerCase()) {
                jq.get("treatmentsGenHistoryModal/saveNewGenHistoryForm.action", {
                    patientUuid: personUUID,
                    cancerType: jq("#genHistoryCancerTypeSelect").val(),
                    cancerStage: jq("#genHistoryCancerStageSelect").val(),
                    cancerDate: jq("#genHistoryDate").val(),
                    cancerAbnormalityBool: jq("#newGenHistoryCancerabnormalitySelect").val(),
                    cancerAbnormalityType: jq("#genHistoryCancerabnormalityTypeSelect").val(),
                    genHistoryCancerPcpName: jq("#genHistoryCancerPcpName").val(),
                    genHistoryCancerPcpEmail: jq("#genHistoryCancerPcpEmail").val(),
                    genHistoryCancerPcpPhone: jq("#genHistoryCancerPcpPhone").val()
                }, function () {
                });
            } else {
                jq.get("treatmentsGenHistoryModal/saveNewGenHistoryForm.action", {
                    patientUuid: personUUID,
                    cancerType: jq("#genHistoryCancerTypeSelect").val(),
                    cancerStage: jq("#genHistoryCancerStageSelect").val(),
                    cancerDate: jq("#genHistoryDate").val(),
                    cancerAbnormalityBool: jq("#newGenHistoryCancerabnormalitySelect").val(),
                    genHistoryCancerPcpName: jq("#genHistoryCancerPcpName").val(),
                    genHistoryCancerPcpEmail: jq("#genHistoryCancerPcpEmail").val(),
                    genHistoryCancerPcpPhone: jq("#genHistoryCancerPcpPhone").val()
                }, function () {
                });
            }
        }
    });
</script>

<body>
<div class="container bgcontent col-sm-offset-2">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputFN">First Name</label>
            <input type="text" class="form-control" id="inputFN" placeholder="First Name">
        </div>

        <div class="form-group col-md-6">
            <label for="inputLN">Last Name</label>
            <input type="text" class="form-control" id="inputLN" placeholder="Last Name">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputBD">Birth Date</label>
            <input type="text" class="form-control" id="inputBD" placeholder="mm/dd/yyyy">
        </div>

        <div class="form-group col-md-6">
            <label for="inputGender">Gender</label>
            <select class="form-control" id="inputGender">
                <option value="M">Male</option>
                <option value="F">Female</option>
            </select>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="patientAddress1">Address</label>
            <input type="text" class="form-control" id="patientAddress1" placeholder="Enter Address">
        </div>

        <div class="form-group col-md-6">
            <label for="patientCity">City</label>
            <input type="text" class="form-control" id="patientCity" placeholder="Enter City">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="patientCountry">Country</label>
            <input type="text" class="form-control" id="patientCountry" placeholder="Enter Country">
        </div>

        <div class="form-group col-md-6">
            <label for="patientZipcode">Zipcode</label>
            <input type="text" class="form-control" id="patientZipcode" placeholder="Enter Zipcode">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="patientEmail">Email address</label>
            <input type="email" class="form-control" id="patientEmail" placeholder="Enter email">
        </div>

        <div class="form-group col-md-6">
            <label for="patientUserName">username</label>
            <input type="text" class="form-control" id="patientUserName" placeholder="Enter username">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="patientPassword">Password</label>
            <input type="password" class="form-control" id="patientPassword">
        </div>
    </div>

    <div class="form-row">
        <% if (treatmentSummaryConcepts) { %>
        <% if (treatmentSummaryConcepts.concepts) { %>
        <% treatmentSummaryConcepts.concepts.each { concept -> %>
        <% /* cancer Type*/ %>
        <% if (concept.uuid == "cdf6d767-2aa3-40b6-ae78-0386eebe2411") { %>
        <div class="form-group col-md-6">
            <label class="reformatText" for="genHistoryCancerTypeSelect">${(concept.getName())}</label>
            <select class="form-control" id="genHistoryCancerTypeSelect">
                <% concept.getAnswers().each { answers -> %>
                <option value="${(answers.answerConcept.uuid)}"
                        class="reformatText">${(answers.answerConcept.getName())}</option>
                <% } %>
            </select>
        </div>
        <% } %>
        <% } %>
        <% treatmentSummaryConcepts.concepts.each { concept -> %>
        <% /* cancer Stage*/ %>
        <% if (concept.uuid == "efa3f9eb-ade4-4ddb-92c9-0fc1119d112d") { %>
        <div class="form-group col-md-6">
            <label class="reformatText" for="genHistoryCancerStageSelect">${(concept.getName())}</label>
            <select class="form-control" id="genHistoryCancerStageSelect">
                <% concept.getAnswers().each { answers -> %>
                <option value="${(answers.answerConcept.uuid)}"
                        class="reformatText">${(answers.answerConcept.getName())}</option>
                <% } %>
            </select>
        </div>

        <% } %>
        <% } %>
    </div>

    <div class="form-row">
        <% treatmentSummaryConcepts.concepts.each { concept -> %>
        <% /* cancer Date*/ %>
        <% if (concept.uuid == "654e32f0-8b57-4d1f-845e-500922e800f6") { %>

        <div class="form-group col-md-6">
            <label class="reformatText" for="genHistoryDate">${(concept.getName())}</label>
            <input class="form-control gen-history-date" id="genHistoryDate" type="text"/>
        </div>
        <% } %>
        <% } %>

        <% treatmentSummaryConcepts.concepts.each { concept -> %>
        <% /* cancer abnormality*/ %>
        <% if (concept.uuid == "395878ae-5108-4aad-8ad8-9b88e812d278") { %>
        <div class="form-group col-md-6">
            <label class="reformatText" for="newGenHistoryCancerabnormalitySelect">${(concept.getName())}</label>
            <select class="form-control" id="newGenHistoryCancerabnormalitySelect">
                <option hidden>Don't Show this option</option>
                <% concept.getAnswers().each { answers -> %>
                <option value="${(answers.answerConcept.uuid)}"
                        class="reformatText">${(answers.answerConcept.getName())}</option>
                <% } %>
            </select>
        </div>
        <% } %>
        <% } %>
    </div>

    <div class="form-row">
        <% treatmentSummaryConcepts.concepts.each { concept -> %>
        <% /* cancer abnormality type*/ %>
        <% if (concept.uuid == "8719adbe-0975-477f-a95f-2fae4d6cbdae") { %>
        <div class="form-group col-md-6" id="canAbnTypeDiv" hiddent>
            <label class="reformatText" for="genHistoryCancerabnormalityTypeSelect">${(concept.getName())}</label>
            <select class="form-control" id="genHistoryCancerabnormalityTypeSelect">
                <% concept.getAnswers().each { answers -> %>
                <option value="${(answers.answerConcept.uuid)}"
                        class="reformatText">${(answers.answerConcept.getName())}</option>
                <% } %>
            </select>
        </div>
        <% } %>
        <% } %>
    </div>
    <label>Primary Care Physician :</label>
    <br><br>

    <div class="form-row">
        <div class="form-group col-md-4"><label for="genHistoryCancerPcpName">Name</label>
            <input class="form-control" id="genHistoryCancerPcpName" type="text"/>
        </div>

        <div class="form-group col-md-4"><label for="genHistoryCancerPcpEmail">Email</label>
            <input class="form-control" id="genHistoryCancerPcpEmail" type="text"/>
        </div>

        <div class="form-group col-md-4"><label for="genHistoryCancerPcpPhone">Phone</label>
            <input class="form-control" id="genHistoryCancerPcpPhone" type="text"/>
        </div>
    </div>
    <% } %>
    <% } %>
    <button id="newPatientSave" class="btn btn-primary">Save</button>
</div>
</body>