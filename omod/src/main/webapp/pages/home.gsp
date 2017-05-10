
${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<script type="text/javascript">
    jq(document).ready(function(){
        if (jq('#isCurrentPatient').val()=="true") {
            jq('#patientPortalNavHome').addClass('active');
        }
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    <input id="isCurrentPatient" value="<% if(contextUser.person !=person) { %>false<% } else{%>true<%} %>" hidden>
    ${ ui.includeFragment("patientportaltoolkit", "profileHeader") }
    <% if(person.isPatient() && securitylevel != 1) { %>
    <ul class="nav nav-pills">
    <li class="active"><a data-toggle="tab" href="#treatments">Treatments</a></li>
    <li><a data-toggle="tab" href="#sideEffects">Side Effects</a></li>
    <li><a data-toggle="tab" href="#followUpCare">Follow up Care</a></li>
    <li><a data-toggle="tab" href="#community">Communities</a></li>
    <li><a data-toggle="tab" href="#symptomManagement">Symptom Management</a></li>
    <li><a data-toggle="tab" href="#preventativeCare">Preventive Care</a></li>
    </ul>

    <!-- eventDataUtilities used in appointments (follow up care) and preventiveCare -->
    <script type="text/javascript" src="${ ui.resourceLink("patientportaltoolkit", "/scripts/eventDataUtilities.js")}"></script>

    <div class="tab-content">
        <div id="treatments" class="tab-pane fade in active">
            ${ ui.includeFragment("patientportaltoolkit", "treatments") }
        </div>
        <div id="sideEffects" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "sideEffects") }
        </div>
        <div id="followUpCare" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "appointments") }
        </div>
        <div id="community" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "community") }
        </div>
        <div id="symptomManagement" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "symptomManagement") }
        </div>
        <div id="preventativeCare" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "preventiveCare") }
        </div>
    </div>
    <% } %>
</div>
</body>
