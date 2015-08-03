<div class="modal fade modal-wide"  id="edit-surgeries-modal" role="dialog" aria-labelledby="editSurgeriesLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editSurgeriesLabel">Surgery</h4>
            </div>
            <div class="modal-body">
                <input id="surgeryEncounterHolder" type="hidden">
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* surgery Type*/ %>
                <% if (questions.uuid=="d409122c-8a0b-4282-a17f-07abad81f278") { %>
                <form class="form-inline" role="form"> <label>${(questions.getName())} </label><br>
                <% questions.answers.each { answers -> %>
                <div class="checkbox">
                    <label><input type="checkbox" class="surgeryTypesInModal" value="${(answers.answerConcept.uuid)}split${(answers.answerConcept.getName())}">${(answers.answerConcept.getName())}</label>
                </div>
                <br>
                <% } %>
                </form>
                <% } %>
                <% } %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* majr complications yes no*/ %>
                <% if (questions.uuid=="99ef1d68-05ed-4f37-b98b-c982e3574138") { %>
                <form class="form-inline" role="form"> <label>${(questions.getName())} </label>
                    <select class="form-control" id="majorComplicationsBoolSelect">
                        <% questions.getAnswers().each { answers -> %>
                        <option  value="${(answers.answerConcept.uuid)}">${(answers.answerConcept.getName())}</option>
                        <option  value="${(answers.answerConcept.uuid)}">${(answers.answerConcept.getName())}</option>
                        <% } %>
                    </select> </form>
                <% } %>
                <% } %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* major complication*/ %>
                <% if (questions.uuid=="c2d9fca3-1e0b-4007-8c3c-b3ebb4e67963") { %>
                <form class="form-inline" role="form"> <label>${(questions.getName())} </label>
                <input class="form-control" id="majorComplicationsTypeAnswer" type="text"/> </form>
                <% } %>
                <% } %>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* surgery Date*/ %>
                <% if (questions.uuid=="87a69397-65ef-4576-a709-ae0a526afd85") { %>
                <form class="form-inline" role="form"> <label>${(questions.getName())} </label>
                    <input class="form-control" id="surgeryDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <label>Surgeon </label>
                <br><br>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="292e2107-b909-4e4a-947f-ce2be8738137") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"> <label>Name </label>
                    <input class="form-control" id="surgeonPcpName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="898a0028-8c65-4db9-a802-1577fce59864") { %>
                <form class="form-inline" role="form"> <label>Email </label>
                    <input class="form-control" id="surgeonPcpEmail" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="9285b227-4054-4830-ac32-5ea78462e8c4") { %>
                <form class="form-inline" role="form"> <label>Phone </label>
                    <input class="form-control" id="surgeonPcpPhone" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
                <label>Surgery Location </label>
                <br><br>
                <% surgeryConcepts.concepts.each { questions -> %>
                <% /* pcp name*/ %>
                <% if (questions.uuid=="329328ab-8e1c-461e-9261-fd4471b1f131") { %>
                <% questions.conceptSets.each { conceptSet -> %>
                <% if (conceptSet.concept.uuid=="47d58999-d3b5-4869-a52e-841e2e6bdbb3") { %>
                <form class="form-inline" role="form"> <label>Institution Name </label>
                    <input class="form-control" id="surgeryInstitutionName" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="bfa752d6-2037-465e-b0a2-c4c2d485ec32") { %>
                <form class="form-inline" role="form"> <label>City </label>
                    <input class="form-control" id="surgeryInstitutionCity" type="text"/>
                </form>
                <% } else if (conceptSet.concept.uuid=="34489100-487e-443a-bf27-1b6869fb9332") { %>
                <form class="form-inline" role="form"> <label>State </label>
                    <input class="form-control" id="surgeryInstitutionState" type="text"/>
                </form>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveSurgeryButton">Save Changes</button>

                </div>
            </div>
        </div>
    </div>
</div>