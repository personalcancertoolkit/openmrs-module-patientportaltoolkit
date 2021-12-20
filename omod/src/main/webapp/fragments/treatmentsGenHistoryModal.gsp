<div class="modal fade modal-wide treatment_form_uniform_label_width edit-genHistory-modal" id="edit-genHistory-modal"
     role="dialog"
     aria-labelledby="editGenHistoryLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editGenHistoryLabel">General History</h4>
            </div>

            <div class="modal-body">
                <input id="genHistEncounterHolder" type="hidden" value="">
                <input id="genHistPatientUuidHolder" type="hidden">
                <% if (genHistoryConcepts) { %>
                <% genHistoryConcepts.concepts.each { question -> %>
                <% /* cancer Type*/ %>
                <% if (question.uuid == "cdf6d767-2aa3-40b6-ae78-0386eebe2411") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(question.getName())}</label>
                    <select class="form-control" id="genHistoryCancerTypeSelect">
                        <% question.getAnswers().each { answers -> %>
                        <option value="${(answers.answerConcept.uuid)}"
                                class="reformatText">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select></form>
                <% } %>
                <% } %>
                <% genHistoryConcepts.concepts.each { question -> %>
                <% /* cancer Stage*/ %>
                <% if (question.uuid == "efa3f9eb-ade4-4ddb-92c9-0fc1119d112d") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(question.getName())}</label>
                    <select class="form-control" id="genHistoryCancerStageSelect">
                        <% question.getAnswers().each { answers -> %>
                        <option value="${(answers.answerConcept.uuid)}"
                                class="reformatText">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select></form>
                <% } %>
                <% } %>

                <% genHistoryConcepts.concepts.each { question -> %>
                <% /* cancer Date*/ %>
                <% if (question.uuid == "654e32f0-8b57-4d1f-845e-500922e800f6") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(question.getName())}</label>
                    <input class="form-control gen-history-date" id="genHistoryDate" type="text"/>
                </form>
                <% } %>
                <% } %>

                <% genHistoryConcepts.concepts.each { question -> %>
                <% /* cancer abnormality*/ %>
                <% if (question.uuid == "395878ae-5108-4aad-8ad8-9b88e812d278") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(question.getName())}</label>
                    <select class="form-control" id="genHistoryCancerabnormalitySelect">
                        <% question.getAnswers().each { answers -> %>
                        <option value="${(answers.answerConcept.uuid)}"
                                class="reformatText">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select></form>
                <% } %>
                <% } %>

                <% genHistoryConcepts.concepts.each { question -> %>
                <% /* cancer abnormality type*/ %>
                <% if (question.uuid == "8719adbe-0975-477f-a95f-2fae4d6cbdae") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(question.getName())}</label>
                    <select class="form-control" id="genHistoryCancerabnormalityTypeSelect">
                        <% question.getAnswers().each { answers -> %>
                        <option value="${(answers.answerConcept.uuid)}"
                                class="reformatText">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select></form>
                <% } %>
                <% } %>

                <label>Primary Care Physician :</label>
                <br><br>
                <% genHistoryConcepts.concepts.each { question -> %>
                <% if (question.uuid == "900f4c74-39ec-4050-881a-e914f97fab50") { %>
                <% question.conceptSets.each { conceptSet -> %>
                <% /* pcp name*/ %>
                <% if (conceptSet.concept.uuid == "c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"><label>Name</label>
                    <input class="form-control" id="genHistoryCancerPcpName" type="text"/>
                </form>
                <% /* pcp email*/ %>
                <% } else if (conceptSet.concept.uuid == "898a0028-8c65-4db9-a802-1577fce59864") { %>
                <form class="form-inline" role="form"><label>Email</label>
                    <input class="form-control" id="genHistoryCancerPcpEmail" type="text"/>
                </form>
                <% /* pcp phone*/ %>
                <% } else if (conceptSet.concept.uuid == "9285b227-4054-4830-ac32-5ea78462e8c4") { %>
                <form class="form-inline" role="form"><label>Phone</label>
                    <input class="form-control" id="genHistoryCancerPcpPhone" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>

            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveGeneralHistorybutton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    jq(document).ready(function () {
        let logData = '';
        jq('#edit-genHistory-modal').on('show.bs.modal', function () {
            logData = '{"cancerType":"' + jq("#genHistoryCancerTypeSelect option:selected").text() + '", ' +
                '"cancerStage":"' + jq("#genHistoryCancerStageSelect option:selected").text() + '", ' +
                '"cancerDate":"' + jq("#genHistoryDate").val() + '", ' +
                '"cancerAbnormalityBool":"' + jq("#genHistoryCancerabnormalitySelect option:selected").text() + '",' +
                '"cancerAbnormalityType":"' + jq("#genHistoryCancerabnormalityTypeSelect option:selected").text() + '",' +
                '"genHistoryCancerPcpName":"' + jq("#genHistoryCancerPcpName").val() + '",' +
                '"genHistoryCancerPcpEmail":"' + jq("#genHistoryCancerPcpEmail").val() + '",' +
                '"genHistoryCancerPcpPhone":"' + jq("#genHistoryCancerPcpPhone").val() + '"}';
        });
        jq('#saveGeneralHistorybutton').click(
            function () {
                logEvent("Treatments_General_History_Saved", logData);
                if (jq("#genHistEncounterHolder").val() == null || jq("#genHistEncounterHolder").val() == '') {
                    jq.get("treatmentsGenHistoryModal/saveNewGenHistoryForm.action", {
                        cancerType: jq("#genHistoryCancerTypeSelect").val(),
                        cancerStage: jq("#genHistoryCancerStageSelect").val(),
                        cancerDate: jq("#genHistoryDate").val(),
                        cancerAbnormalityBool: jq("#genHistoryCancerabnormalitySelect").val(),
                        cancerAbnormalityType: jq("#genHistoryCancerabnormalityTypeSelect").val(),
                        genHistoryCancerPcpName: jq("#genHistoryCancerPcpName").val(),
                        genHistoryCancerPcpEmail: jq("#genHistoryCancerPcpEmail").val(),
                        genHistoryCancerPcpPhone: jq("#genHistoryCancerPcpPhone").val(),
                        patientUuid: jq("#genHistPatientUuidHolder").val()
                    }, function () {
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                } else {
                    jq.get("treatmentsGenHistoryModal/saveGenHistoryForm.action", {
                        encounterId: jq("#genHistEncounterHolder").val(),
                        cancerType: jq("#genHistoryCancerTypeSelect").val(),
                        cancerStage: jq("#genHistoryCancerStageSelect").val(),
                        cancerDate: jq("#genHistoryDate").val(),
                        cancerAbnormalityBool: jq("#genHistoryCancerabnormalitySelect").val(),
                        cancerAbnormalityType: jq("#genHistoryCancerabnormalityTypeSelect").val(),
                        genHistoryCancerPcpName: jq("#genHistoryCancerPcpName").val(),
                        genHistoryCancerPcpEmail: jq("#genHistoryCancerPcpEmail").val(),
                        genHistoryCancerPcpPhone: jq("#genHistoryCancerPcpPhone").val(),
                        patientUuid: jq("#genHistPatientUuidHolder").val()
                    }, function () {
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                }
            });
        var genHistdatePicker = jq(".gen-history-date").datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function () {
            genHistdatePicker.hide();
        }).data('datepicker');
    });
</script>