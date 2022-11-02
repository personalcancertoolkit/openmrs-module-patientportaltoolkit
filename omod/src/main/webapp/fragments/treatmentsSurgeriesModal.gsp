<div class="modal fade modal-wide treatment_form_uniform_label_width edit-surgeries-modal" id="edit-surgeries-modal" role="dialog"
     aria-labelledby="editSurgeriesLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editSurgeriesLabel">Surgery</h4>
            </div>

            <div class="modal-body">
                <input id="surgeryEncounterHolder" type="hidden">
                <input id="surgeryPatientUuidHolder" type="hidden">
                <% if (surgeryConcepts) { %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* surgery Type*/ %>
                <% if (questions.uuid == "d409122c-8a0b-4282-a17f-07abad81f278") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(questions.getName())}</label><br>
                    <% questions.answers.each { answers -> %>
                    <div class="checkbox">
                        <label><input type="checkbox" class="surgeryTypesInModal"
                                      value="${(answers.answerConcept.uuid)}split${(answers.answerConcept.getName())}"><span
                                class="reformatText">${(answers.answerConcept.getName())}</span></label>
                    </div>
                    <br>
                    <% } %>
                </form>
                <% } %>
                <% } %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* other Surgery  name*/ %>
                <% if (questions.uuid=="683429f5-550a-463a-803b-a3efb6630c7f") { %>
                <form class="form-inline" role="form"> <label>Other Surgery Type Name </label>
                    <input class="form-control" id="otherSurgeryName" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* surgery Date*/ %>
                <% if (questions.uuid == "87a69397-65ef-4576-a709-ae0a526afd85") { %>
                <form class="form-inline" role="form"><label class="reformatText">${(questions.getName())}</label>
                    <input class="form-control" id="surgeryDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <label>Surgeon</label>
                <br><br>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid == "292e2107-b909-4e4a-947f-ce2be8738137") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid == "c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"><label>Name</label>
                    <input class="form-control" id="surgeonPcpName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid == "898a0028-8c65-4db9-a802-1577fce59864") { %>
                <form class="form-inline" role="form"><label>Email</label>
                    <input class="form-control" id="surgeonPcpEmail" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid == "9285b227-4054-4830-ac32-5ea78462e8c4") { %>
                <form class="form-inline" role="form"><label>Phone</label>
                    <input class="form-control" id="surgeonPcpPhone" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <label>Surgery Location</label>
                <br><br>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid == "329328ab-8e1c-461e-9261-fd4471b1f131") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid == "47d58999-d3b5-4869-a52e-841e2e6bdbb3") { %>
                <form class="form-inline" role="form"><label>Institution Name</label>
                    <input class="form-control" id="surgeryInstitutionName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid == "bfa752d6-2037-465e-b0a2-c4c2d485ec32") { %>
                <form class="form-inline" role="form"><label>City</label>
                    <input class="form-control" id="surgeryInstitutionCity" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid == "34489100-487e-443a-bf27-1b6869fb9332") { %>
                <form class="form-inline" role="form"><label>State</label>
                    <input class="form-control" id="surgeryInstitutionState" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>

            <div class="modal-footer">
                <div id="surgeryErrorDetails" style="color: red" hidden></div>

                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveSurgeryButton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    jq(document).ready(function () {
        let logData = '';
        jq('#edit-surgeries-modal').on('show.bs.modal', function () {
            let existingSurgeryTypeList = '';
            jq('.surgeryTypesInModal').each(function () {
                if (jq(this).is(':checked')) {
                    existingSurgeryTypeList = existingSurgeryTypeList + (jq(this).val().split('split')[1]) + ",";
                }
            });
            logData = '{"surgeryTypes":"' + existingSurgeryTypeList + '", ' +
                '"surgeryComplications":"' + jq("#majorComplicationsBoolSelect option:selected").text() + '", ' +
                '"majorComplicationsTypeAnswer":"' + jq("#majorComplicationsTypeAnswer").val() + '", ' +
                '"surgeryDate":"' + jq("#surgeryDate").val() + '",' +
                '"surgeonPcpName":"' + jq("#surgeonPcpName").val() + '",' +
                '"surgeonPcpEmail":"' + jq("#surgeonPcpEmail").val() + '",' +
                '"surgeonPcpPhone":"' + jq("#surgeonPcpPhone").val() + '",' +
                '"surgeryInstitutionName":"' + jq("#surgeryInstitutionName").val() + '",' +
                '"surgeryInstitutionCity":"' + jq("#surgeryInstitutionCity").val() + '",' +
                '"surgeryInstitutionState":"' + jq("#surgeryInstitutionState").val() + '"}';
        });
        jq('#saveSurgeryButton').click(
            function () {
                logEvent("Treatments_Surgery_Saved", logData);
                var surgeryTypeList = '';
                var isCheckedExists = 0;
                jq('.surgeryTypesInModal').each(function () {
                    if (jq(this).is(':checked')) {
                        surgeryTypeList = surgeryTypeList + (jq(this).val().split('split')[0]) + "split";
                        isCheckedExists = 1;
                    }
                });
                if (isCheckedExists == 0) {
                    jq('#surgeryErrorDetails').text("Please select at least one surgery");
                    jq('#surgeryErrorDetails').show();
                } else if (jq("#surgeryEncounterHolder").val() == null || jq("#surgeryEncounterHolder").val() == '') {
                    jq.get("treatmentsSurgeriesModal/saveNewSurgeryForm.action", {
                        surgeryTypes: surgeryTypeList,
                        otherSurgeryName: jq("#otherSurgeryName").val(),
                        surgeryComplications: jq("#majorComplicationsBoolSelect").val(),
                        majorComplicationsTypeAnswer: jq("#majorComplicationsTypeAnswer").val(),
                        surgeryDate: jq("#surgeryDate").val(),
                        surgeonPcpName: jq("#surgeonPcpName").val(),
                        surgeonPcpEmail: jq("#surgeonPcpEmail").val(),
                        surgeonPcpPhone: jq("#surgeonPcpPhone").val(),
                        surgeryInstitutionName: jq("#surgeryInstitutionName").val(),
                        surgeryInstitutionCity: jq("#surgeryInstitutionCity").val(),
                        surgeryInstitutionState: jq("#surgeryInstitutionState").val(),
                        patientUuid: jq("#surgeryPatientUuidHolder").val()
                    }, function () {
                    });
                    setTimeout(
                        function () {
                            location.reload();
                        }, 2000);
                } else {
                    jq.get("treatmentsSurgeriesModal/saveSurgeryForm.action", {
                        encounterId: jq("#surgeryEncounterHolder").val(),
                        surgeryTypes: surgeryTypeList,
                        otherSurgeryName: jq("#otherSurgeryName").val(),
                        surgeryComplications: jq("#majorComplicationsBoolSelect").val(),
                        majorComplicationsTypeAnswer: jq("#majorComplicationsTypeAnswer").val(),
                        surgeryDate: jq("#surgeryDate").val(),
                        surgeonPcpName: jq("#surgeonPcpName").val(),
                        surgeonPcpEmail: jq("#surgeonPcpEmail").val(),
                        surgeonPcpPhone: jq("#surgeonPcpPhone").val(),
                        surgeryInstitutionName: jq("#surgeryInstitutionName").val(),
                        surgeryInstitutionCity: jq("#surgeryInstitutionCity").val(),
                        surgeryInstitutionState: jq("#surgeryInstitutionState").val(),
                        patientUuid: jq("#surgeryPatientUuidHolder").val()
                    }, function () {
                    });
                    setTimeout(
                        function () {
                            location.reload();
                        }, 2000);
                }
            });
        var surgerydatePicker = jq("#surgeryDate").datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function () {
            surgerydatePicker.hide();
        }).data('datepicker');
        jq('#edit-surgeries-modal').scroll(function(){
            jq("#surgeryDate").datepicker("hide");
            jq("#surgeryDate").blur();
        });
    });
</script>