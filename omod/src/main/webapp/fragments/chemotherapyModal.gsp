<div class="modal fade modal-wide"  id="edit-chemotherapies-modal" role="dialog" aria-labelledby="editChemotherapiesLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editSurgeriesLabel">Chemotherapy</h4>
            </div>
            <div class="modal-body">
                <input id="chemotherapyEncounterHolder" type="hidden">
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* chemotherapy medication used*/ %>
                <% if (questions.uuid=="8481b9da-74e3-45a9-9124-d69ab572d636") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(questions.getName())}</label><br>
                    <% questions.answers.each { answers -> %>
                    <div class="checkbox">
                        <label><input type="checkbox" class="chemotherapyMedTypesInModal" value="${(answers.answerConcept.uuid)}split${(answers.answerConcept.getName())}"><span class="reformatText">${(answers.answerConcept.getName())}</span></label>
                    </div>
                    <br>
                    <% } %>
                </form>
                <% } %>
                <% } %>
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* central Line yes no*/ %>
                <% if (questions.uuid=="361b7f9b-a985-4b18-9055-03af3b41b8b3") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(questions.getName())} </label>
                    <select class="form-control" id="centralLineBoolSelect">
                        <% questions.getAnswers().each { answers -> %>
                        <option  value="${(answers.answerConcept.uuid)}" class="reformatText">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select> </form>
                <% } %>
                <% } %>
                <label>When Received?</label>
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* chemo start Date*/ %>
                <% if (questions.uuid=="85c3a99e-0598-4c63-912b-796dee9c75b2") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(questions.getName())} </label>
                    <input class="form-control" id="chemoStartDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* chemo end Date*/ %>
                <% if (questions.uuid=="7dd8b8aa-b0f1-4eb1-862d-b6d737bdd315") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(questions.getName())} </label>
                    <input class="form-control" id="chemoendDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <label>Oncologist</label>
                <br><br>
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="5cbdb3c4-a531-4da5-b1e3-d5fd6420693a") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"> <label>Name </label>
                    <input class="form-control" id="oncologistPcpName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="898a0028-8c65-4db9-a802-1577fce59864") { %>
                <form class="form-inline" role="form"> <label>Email </label>
                    <input class="form-control" id="oncologistPcpEmail" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="9285b227-4054-4830-ac32-5ea78462e8c4") { %>
                <form class="form-inline" role="form"> <label>Phone </label>
                    <input class="form-control" id="oncologistPcpPhone" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <label>Chemotherapy Location</label>
                <br><br>
                <% chemotherapyConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="329328ab-8e1c-461e-9261-fd4471b1f131") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="47d58999-d3b5-4869-a52e-841e2e6bdbb3") { %>
                <form class="form-inline" role="form"> <label>Institution Name </label>
                    <input class="form-control" id="chemotherapyInstitutionName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="bfa752d6-2037-465e-b0a2-c4c2d485ec32") { %>
                <form class="form-inline" role="form"> <label>City </label>
                    <input class="form-control" id="chemotherapyInstitutionCity" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="34489100-487e-443a-bf27-1b6869fb9332") { %>
                <form class="form-inline" role="form"> <label>State </label>
                    <input class="form-control" id="chemotherapyInstitutionState" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveChemotherapyButton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>