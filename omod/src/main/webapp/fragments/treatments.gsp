<div>
    <div class="clearfix">
        <h4>General History</h4>

        <div class="pull-right">
            <a href="/general-history-form"
               class="glyphicon glyphicon-pencil"></a>
        </div>

        <div>
            <% if (treatmentsummary) { %>
            <% treatmentsummary.each { genhistory -> %>
            <div>
                <label>
                ${(genhistory.cancerType)}
                &ensp;
                ${(genhistory.cancerStage)}
                </label>
                <span><small>&emsp;${(genhistory.diagnosisDate)}</small></span>
            </div>
            <% if (genhistory.hasGeneticOrPredisposingAbnormality) { %>
            <div>
                <label>Genetic or Predisposing Abnormality&emsp;</label>
                <span>${(genhistory.geneticOrPredisposingAbnormality)}</span>
            </div>
            <% } %>
            <div>
                <label>Primary Care Provider&emsp;</label>
                <span>${(genhistory.pcpName)}
                    <small>&emsp;${(genhistory.pcpPhone)}</small>
                    <small>&emsp;${(genhistory.pcpEmail)}</small>
                </span>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>

    <hr/>

    <div class="clearfix">
        <h4>Surgeries&emsp;
            <a href="/treatments/all-treatments/surgeries/procedure-form"
               class="btn btn-primary btn-sm">Add</a>
        </h4>
    </div>

    <div>
        <div class="pull-right">
            <a href="/treatments/all-treatments/surgeries/procedure-form"
               class="glyphicon glyphicon-pencil"></a>
        </div>
        <% if (surgeryencounters) { %>
        <% surgeryencounters.each { encounter -> %>
        <% if (encounter.obs) { %>
        <% encounter.obs.each { obs -> %>
        <label>${(obs.concept.getName())}</label>
        <% if (obs.concept.datatype.isText()) { %> <span><small>&emsp;${(obs.valueText)}</small></span> <% } %>
    <% if (obs.concept.datatype.isCoded()) { %> <span><small>&emsp;${(obs.valueCoded.getName())}</small></span><% } %>
    <% if (obs.concept.datatype.isDate()) { %> <span><small>&emsp;${(obs.valueDate)}</small></span><% } %>
        <br>
        <% } %>
        <% } %>
        <% } %>
        <% } %>
    </div>

    <hr/>

    <div>
        <div class="clearfix">
            <h4>Chemotherapies&emsp;
                <a href="../NotFoundPage.js"
                   class="btn btn-primary btn-sm">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <div class="pull-right">
                    <a href="/treatments/all-treatments/surgeries/procedure-form"
                       class="glyphicon glyphicon-pencil"></a>
                </div>
                <% if (chemotherapyencounters) { %>
                <% chemotherapyencounters.each { encounter -> %>
                <% if (encounter.obs) { %>
                <% encounter.obs.each { obs -> %>
                <label>${(obs.concept.getName())}</label>
                <% if (obs.concept.datatype.isText()) { %> <span><small>&emsp;${(obs.valueText)}</small></span> <% } %>
            <% if (obs.concept.datatype.isCoded()) { %> <span><small>&emsp;${(obs.valueCoded.getName())}</small>
            </span><% } %>
            <% if (obs.concept.datatype.isDate()) { %> <span><small>&emsp;${(obs.valueDate)}</small></span><% } %>
                <br>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>

        </div>
    </div>

    <div>
        <div class="clearfix">
            <h4>Radiation Surgery&emsp;
                <a href="../NotFoundPage.js"
                   class="btn btn-primary btn-sm">Add</a>
            </h4>
        </div>

        <div>
            <div class="clearfix">
                <div class="pull-right">
                    <a href="/treatments/all-treatments/surgeries/procedure-form"
                       class="glyphicon glyphicon-pencil"></a>
                </div>
                <% if (radiationencounters) { %>
                <% radiationencounters.each { encounter -> %>
                <% if (encounter.obs) { %>
                <% encounter.obs.each { obs -> %>
                <label>${(obs.concept.getName())}</label>
                <% if (obs.concept.datatype.isText()) { %> <span><small>&emsp;${(obs.valueText)}</small></span> <% } %>
            <% if (obs.concept.datatype.isCoded()) { %> <span><small>&emsp;${(obs.valueCoded.getName())}</small>
            </span><% } %>
            <% if (obs.concept.datatype.isDate()) { %> <span><small>&emsp;${(obs.valueDate)}</small></span><% } %>
                <br>
                <% } %>
                <% } %>
                <% } %>
                <% } %>
            </div>

        </div>
    </div>
</div>