<div class="modal fade modal-wide treatment_form_uniform_label_width"  id="edit-radiation-modal" role="dialog" aria-labelledby="editRadiationsLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editRadiationsLabel">Radiation</h4>
            </div>
            <div class="modal-body">
                <input id="radiationEncounterHolder" type="hidden">
                <% if (radiationConcepts) { %>
                <% radiationConcepts.concepts.each { questions -> %>
                <% /* radiation type used*/ %>
                <% if (questions.uuid=="42fb7bb5-f840-4518-814c-893813211cba") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(questions.getName())}</label><br>
                    <% questions.answers.each { answers -> %>
                    <div class="checkbox">
                        <label><input type="checkbox" class="radiationTypesInModal" value="${(answers.answerConcept.uuid)}split${(answers.answerConcept.getName())}"><span class="reformatText">${(answers.answerConcept.getName())}</span></label>
                    </div>
                    <br>
                    <% } %>
                </form>
                <% } %>
                <% } %>
                <label>When Received?</label>
                <% radiationConcepts.concepts.each { questions -> %>
                <% /* radiation start Date*/ %>
                <% if (questions.uuid=="85c3a99e-0598-4c63-912b-796dee9c75b2") { %>
                <form class="form-inline" role="form"> <label class="reformatText">Radiation start date </label>
                    <input class="form-control" id="radiationStartDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% radiationConcepts.concepts.each { questions -> %>
                <% /* radiation end Date*/ %>
                <% if (questions.uuid=="7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315") { %>
                <form class="form-inline" role="form"> <label class="reformatText">Radiation end date </label>
                    <input class="form-control" id="radiationEndDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <label>Radiation Therapist</label>
                <br><br>
                <% radiationConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="f031cc84-5eb1-4d64-beb5-c3c6bd9b915c") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"> <label>Name </label>
                    <input class="form-control" id="radiologistPcpName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="898a0028-8c65-4db9-a802-1577fce59864") { %>
                <form class="form-inline" role="form"> <label>Email </label>
                    <input class="form-control" id="radiologistPcpEmail" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="9285b227-4054-4830-ac32-5ea78462e8c4") { %>
                <form class="form-inline" role="form"> <label>Phone </label>
                    <input class="form-control" id="radiologistPcpPhone" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <label>Radiation Location</label>
                <br><br>
                <% radiationConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="329328ab-8e1c-461e-9261-fd4471b1f131") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="47d58999-d3b5-4869-a52e-841e2e6bdbb3") { %>
                <form class="form-inline" role="form"> <label>Institution Name </label>
                    <input class="form-control" id="radiologistInstitutionName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="bfa752d6-2037-465e-b0a2-c4c2d485ec32") { %>
                <form class="form-inline" role="form"> <label>City </label>
                    <input class="form-control" id="radiologistInstitutionCity" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="34489100-487e-443a-bf27-1b6869fb9332") { %>
                <form class="form-inline" role="form"> <label>State </label>
                    <input class="form-control" id="radiologistInstitutionState" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <div id="radiationErrorDetails" style="color: red" hidden></div>
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveRadiationButton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>