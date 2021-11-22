${ui.includeFragment("patientportaltoolkit", "headerForApp")}
<script>
    jq(document).ready(function () {

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
                <option style="display: none">Don't Show this option</option>
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
        <div class="form-group col-md-6" id="canAbnTypeDiv" style="display: none">
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