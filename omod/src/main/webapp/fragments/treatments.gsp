<style>
.treatment_form_uniform_label_width label{
    width:300px;   
}

</style>

${ ui.includeFragment("patientportaltoolkit", "treatmentsGenHistoryModal") }
<div>
    <div class="clearfix">


        <h4>General History</h4>


        <div>
            <% if (genhistory) { %>
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
            <% if (genhistory.hasGeneticOrPredisposingAbnormality) { %>
            <div>
                <label>Genetic or Predisposing Abnormality&emsp;</label>
                <span id="${(genhistory.encounterUuid)}geneticOrPredisposingAbnormality" class="reformatText">${(genhistory.geneticOrPredisposingAbnormality)}</span>
            </div>
            <% } %>
            <div>
                <label>Primary Care Provider&emsp;</label>
            <span><span id="${(genhistory.encounterUuid)}genHistoryCancerPcpName">${(genhistory.pcpName)}</span>
                    <small id="${(genhistory.encounterUuid)}genHistoryCancerPcpPhone">&emsp;${(genhistory.pcpPhone)}</small>
                    <small id="${(genhistory.encounterUuid)}genHistoryCancerPcpEmail">${(genhistory.pcpEmail)}</small>
                </span>
            </div>
            <% } %>
        </div>
    </div>

    <hr/>
    ${ ui.includeFragment("patientportaltoolkit", "treatmentsSurgeriesModal") }
    <div class="clearfix">
        <h4>Surgeries&emsp;
            <a class="btn btn-primary btn-sm" onclick="logEvent('clicked_add_surgeries','')"  data-toggle="modal" data-target="#edit-surgeries-modal">Add</a>
        </h4>
    </div>

    <div>
        <div>
            <% if (surgeryencounters) { %>
            <% surgeryencounters.each { surgery -> %>
            <% if(isACareGiver != 1) { %>
            <div class="pull-right">
                <a id="${(surgery.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editSurgeryButton"  data-toggle="modal" data-target="#edit-surgeries-modal"></a>
            </div>
            <% } %>
            <div class="clearfix">
                <div class="pull-left">
                    <h5><label><% surgery.surgeryTypes.each { surgeryType -> %> <span class="${(surgery.encounterUuid)}surgeryType reformatText" id="${(surgery.encounterUuid)}surgeryType${(surgeryType)}">${(surgeryType)}</span>; <% } %></label> &emsp;<small id="${(surgery.encounterUuid)}surgeryDate"  >${pptutil.formatDate((surgery.surgeryDate))}</small></h5>
                    <% if (surgery.hasMajorComplications) { %>
                    <div>
                        <label>Major Complications&emsp;</label>
                        <span id="${(surgery.encounterUuid)}surgeryComplications">${(surgery.majorComplications)}</span>
                    </div>
                    <% } %>
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
                        &emsp;<small id="${(surgery.encounterUuid)}surgeryPCPEmail">${(surgery.pcpEmail)}</small>
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
                <a class="btn btn-primary btn-sm" onclick="logEvent('clicked_add_chemotherapy','')" data-toggle="modal" data-target="#edit-chemotherapies-modal">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <% if (chemotherapyencounters) { %>
                <% chemotherapyencounters.each { chemotherapy -> %>
                <% if(isACareGiver != 1) { %>
                <div class="pull-right">
                    <a id="${(chemotherapy.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editChemotherapyButton"  data-toggle="modal" data-target="#edit-chemotherapies-modal"></a>
                </div>
                <% } %>
                <div class="clearfix">
                    <div class="pull-left">
                        <h5> <label> <% chemotherapy.chemoMedications.each { chemotherapymed -> %> <span class="${(chemotherapy.encounterUuid)}chemotherapymed reformatText" id="${(chemotherapy.encounterUuid)}chemotherapymed${(chemotherapymed)}">${(chemotherapymed)}</span>; <% } %></label>
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
                <a class="btn btn-primary btn-sm" onclick="logEvent('clicked_add_radiation','')" data-toggle="modal" data-target="#edit-radiation-modal">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <% if (radiationencounters) { %>
                <% radiationencounters.each { radiation -> %>
                <% if(isACareGiver != 1) { %>
                <div class="pull-right">
                    <a id="${(radiation.encounterUuid)}" class="no-underline-edit fa fa-pencil fa-lg editRadiationButton" data-toggle="modal" data-target="#edit-radiation-modal"></a>
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