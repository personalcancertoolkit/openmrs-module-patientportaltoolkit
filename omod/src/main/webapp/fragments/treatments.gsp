<style>
.treatment_form_uniform_label_width label{
    width:300px;   
}

</style>
<div class="modal fade treatment_form_uniform_label_width delete-treatment-modal" id="delete-treatment-modal" role="dialog"
     aria-labelledby="deleteTreatmentLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteTreatmentLabel">Delete Treatment?</h4>
            </div>

            <div class="modal-body">
                <input id="treatmentEncounterHolder" type="hidden">
                Are you sure you want to delete the treatment?
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="deleteTreatmentButton">Delete Treatment</button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

${ ui.includeFragment("patientportaltoolkit", "treatmentsGenHistoryModal") }
<div>
    <div class="clearfix">
        <input id="treatmentsPatientUuidHolder" type="hidden" value="${patientUUID}">

        <div class="clearfix">
            <h4>
                General History&emsp;
                <% if (genhistory == null) { %>
                    <a class="btn btn-primary btn-sm addGenHistButton" 
                        onclick="logEvent('clicked_add_genhist','')"  
                        data-toggle="modal" 
                        data-target="#edit-genHistory-modal">Add</a>
                <% }%>
            </h4>
        </div>
        <div>
            <% if (genhistory  && genhistory !=null) { %>
            <% if(isACareGiver != 1) { %>
            <div class="pull-right">
                <a id="${(genhistory.encounterUuid)}"
                   class="no-underline-edit fa fa-pencil fa-lg editGenHistButton" data-toggle="modal" data-target="#edit-genHistory-modal"></a>
            </div>
            <% } %>
            <div>
                <label>
                    <span id="${(genhistory.encounterUuid)}cancerType" class="reformatText">${(genhistory.cancerType)}</span>
                &ensp;
                    <span id="${(genhistory.encounterUuid)}cancerStage" class="reformatText">${(genhistory.cancerStage)}</span>
                </label>
                <span>&emsp;<small id="${(genhistory.encounterUuid)}diagnosisDate">${pptutil.formatDate((genhistory.diagnosisDate))}</small></span>
            </div>
            <div>
                <label>Primary Care Provider&emsp;</label>
                <span>
                <% if(genhistory.pcpName != null ) {%>
                    <span id="${(genhistory.encounterUuid)}genHistoryCancerPcpName">${(genhistory.pcpName)}</span>
                <%}%>
                <% if(genhistory.pcpPhone != null ) {%>
                    &emsp; <small id="${(genhistory.encounterUuid)}genHistoryCancerPcpPhone">${(genhistory.pcpPhone)}</small>
                <%}%>
                <% if(genhistory.pcpEmail != null ) {%>
                    <small id="${(genhistory.encounterUuid)}genHistoryCancerPcpEmail">${(genhistory.pcpEmail)}</small>
                <%}%>
                </span>
            </div>
            <% } %>
        </div>
    </div>
    <hr/>
    ${ ui.includeFragment("patientportaltoolkit", "treatmentsSurgeriesModal") }
    <div class="clearfix">
        <h4>Surgeries&emsp;
            <a class="btn btn-primary btn-sm addSurgeryButton" onclick="logEvent('clicked_add_surgeries','')"  data-toggle="modal" data-target="#edit-surgeries-modal">Add</a>
        </h4>
    </div>

    <div>
        <div>
            <% if (surgeryencounters && surgeryencounters !=null) { %>
            <% surgeryencounters.each { surgery -> %>
            <% if(isACareGiver != 1) { %>
            <div class="pull-right">
                <a id="${(surgery.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editSurgeryButton"  data-toggle="modal" data-target="#edit-surgeries-modal"></a>
            &nbsp
                <a id="${(surgery.encounterUuid)}" class="no-underline-edit fa fa-trash fa-lg deleteTreatmentButton" data-toggle="modal" data-target="#delete-treatment-modal"></a>
            </div>
            <% } %>
            <div class="clearfix">
                <div class="pull-left">
                    <h5>
                        <label>
                            <% surgery.surgeryTypes.each { surgeryType -> %> 
                                <span class="${(surgery.encounterUuid)}surgeryType reformatText" id="${(surgery.encounterUuid)}surgeryType${(surgeryType)}">${(surgeryType)}</span>; <% } %>
                        </label> 
                        &emsp;<small id="${(surgery.encounterUuid)}surgeryDate">
                            <%if(surgery.surgeryDate) {%>
                                ${pptutil.formatDate((surgery.surgeryDate))}
                            <% }%>
                        </small> 
                        <% if (surgery.otherSurgeryName) { %>&emsp;<span id="${(surgery.encounterUuid)}otherSurgeryName">${(surgery.otherSurgeryName)}</span><% } %>
                    </h5>
                    <div>
                    <label>Surgery Location&emsp;</label>
                        <span id="${(surgery.encounterUuid)}surgeryinstituteName">${(surgery.institutionName)}</span>
                    &emsp;
                        <span id="${(surgery.encounterUuid)}surgeryCity">${(surgery.institutionCity)}</span>
                    &emsp;
                        <span id="${(surgery.encounterUuid)}surgeryState">${(surgery.institutionState)}</span>
                    </div>
                    <div>
                        <label>Surgeon&emsp;</label>
                        <span><span id="${(surgery.encounterUuid)}surgeryPCPName">${(surgery.pcpName)}</span>
                        &emsp;<small id="${(surgery.encounterUuid)}surgeryPCPPhone">${(surgery.pcpPhone)}</small>
                        </span>
                    </div>
                </div>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>

    <hr/>

    ${ ui.includeFragment("patientportaltoolkit", "treatmentsChemotherapyModal") }
    <div>
        <div class="clearfix">
            <h4>Chemotherapies&emsp;
                <a class="btn btn-primary btn-sm addChemotherapyButton" onclick="logEvent('clicked_add_chemotherapy','')" data-toggle="modal" data-target="#edit-chemotherapies-modal">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <% if (chemotherapyencounters) { %>
                <% chemotherapyencounters.each { chemotherapy -> %>
                <% if(isACareGiver != 1) { %>
                <div class="pull-right">
                    <a id="${(chemotherapy.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editChemotherapyButton"  data-toggle="modal" data-target="#edit-chemotherapies-modal"></a>
                &nbsp
                <a id="${(chemotherapy.encounterUuid)}" class="no-underline-edit fa fa-trash fa-lg deleteTreatmentButton" data-toggle="modal" data-target="#delete-treatment-modal"></a>
                </div>
                <% } %>
                <div class="clearfix">
                    <div class="pull-left">
                        <h5> <label> <% chemotherapy.chemoMedications.each { chemotherapymed -> %> <span class="${(chemotherapy.encounterUuid)}chemotherapymed reformatText" id="${(chemotherapy.encounterUuid)}chemotherapymed${(chemotherapymed)}">${(chemotherapymed)}</span>; <% } %></label> <% if (chemotherapy.otherChemoMedicationName) { %>&emsp;<span id="${(chemotherapy.encounterUuid)}otherChemotherapyMedicationName">${(chemotherapy.otherChemoMedicationName)}</span><% } %>
                            <small> <% if (chemotherapy.chemoStartDate) { %>&emsp;<span id="${(chemotherapy.encounterUuid)}chemotherapyStartDate">${pptutil.formatDate((chemotherapy.chemoStartDate))}</span><% } %>  <% if (chemotherapy.chemoEndDate) { %>&ndash;<span id="${(chemotherapy.encounterUuid)}chemotherapyEndDate" > ${pptutil.formatDate((chemotherapy.chemoEndDate))}</span><% } %></small>
                        </h5>
                        <div class="">
                            <label>Central Line</label> &ndash; <span><small id="${(chemotherapy.encounterUuid)}centralLine"><% if (chemotherapy.centralLine) { %>Yes<% } else{ %>No<% } %></small></span>
                        </div>
                        <div class="">
                            <label>Chemotherapy Location&emsp;</label>
                            <% if (chemotherapy.institutionName) { %> <span id="${(chemotherapy.encounterUuid)}chemotherapyinstituteName">${(chemotherapy.institutionName)}</span><% } %>
                        <% if (chemotherapy.institutionCity) { %>&emsp;
                            <span id="${(chemotherapy.encounterUuid)}chemotherapyCity">${(chemotherapy.institutionCity)}</span><% } %>
                        <% if (chemotherapy.institutionState) { %>&emsp;
                            <span id="${(chemotherapy.encounterUuid)}chemotherapyState">${(chemotherapy.institutionState)}</span><% } %>
                        </div>
                        <div>
                            <label>Oncologist&emsp;</label>
                            <span>  <% if (chemotherapy.pcpName) { %><span id="${(chemotherapy.encounterUuid)}chemotherapyPCPName">${(chemotherapy.pcpName)}</span><% } %>
                            <% if (chemotherapy.pcpPhone) { %>&emsp;<small id="${(chemotherapy.encounterUuid)}chemotherapyPCPPhone">${(chemotherapy.pcpPhone)}</small><% } %>
                            <% if (chemotherapy.pcpEmail) { %>&emsp;<small id="${(chemotherapy.encounterUuid)}chemotherapyPCPEmail">${(chemotherapy.pcpEmail)}</small><% } %>
                            </span>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>

        </div>
    </div>
    <hr/>

    ${ ui.includeFragment("patientportaltoolkit", "treatmentsRadiationModal") }
    <div>
        <div class="clearfix">
            <h4>Radiation Therapy&emsp;
                <a class="btn btn-primary btn-sm addRadiationButton" onclick="logEvent('clicked_add_radiation','')" data-toggle="modal" data-target="#edit-radiation-modal">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <% if (radiationencounters) { %>
                <% radiationencounters.each { radiation -> %>
                <% if(isACareGiver != 1) { %>
                <div class="pull-right">
                    <a id="${(radiation.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editRadiationButton" data-toggle="modal" data-target="#edit-radiation-modal"></a>
                &nbsp
                <a id="${(radiation.encounterUuid)}" class="no-underline-edit fa fa-trash fa-lg deleteTreatmentButton" data-toggle="modal" data-target="#delete-treatment-modal"></a>
                </div>
                <% } %>
                <div class="clearfix">
                    <div class="pull-left">
                        <h5>  <label> <% radiation.radiationTypes.each { radiationType -> %> <span class="${(radiation.encounterUuid)}radiationType reformatText" id="${(radiation.encounterUuid)}radiationType${(radiationType)}">${(radiationType)}</span>; <% } %></label>
                            <small> <% if (radiation.startDate) { %>&emsp;<span id="${(radiation.encounterUuid)}radStartDate">${pptutil.formatDate((radiation.startDate))}</span><% } %>  <% if (radiation.endDate) { %>&ndash; <span id="${(radiation.encounterUuid)}radEndDate">${pptutil.formatDate((radiation.endDate))}</span><% } %></small>
                        </h5>
                        <div class="">
                            <label>Radiation Location&emsp;</label>
                            <% if (radiation.institutionName) { %> <span id="${(radiation.encounterUuid)}radinstituteName">${(radiation.institutionName)}</span><% } %>
                        <% if (radiation.institutionCity) { %>   &emsp;
                            <span id="${(radiation.encounterUuid)}radCity">${(radiation.institutionCity)}</span><% } %>
                        <% if (radiation.institutionState) { %> &emsp;
                            <span id="${(radiation.encounterUuid)}radState">${(radiation.institutionState)}</span><% } %>
                        </div>
                        <div>
                            <label>Radiation Oncologist&emsp;</label>
                            <span>  <% if (radiation.pcpName) { %><span id="${(radiation.encounterUuid)}radPCPName">${(radiation.pcpName)}</span><% } %>
                            <% if (radiation.pcpPhone) { %> &emsp; <small id="${(radiation.encounterUuid)}radPCPPhone">${(radiation.pcpPhone)}</small><% } %>
                            <% if (radiation.pcpEmail) { %>&emsp; <small id="${(radiation.encounterUuid)}radPCPEmail">${(radiation.pcpEmail)}</small><% } %>
                            </span>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>

        </div>
    </div>
</div>