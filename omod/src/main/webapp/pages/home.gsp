
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
    <label><small><span class="fa fa-info-circle fa-lg"></span> &nbsp;The below tabs will be visible to the people you share your medical privilege with (including your healthy behavior tab)</small></label>
    <ul class="nav nav-pills">
    <li class="active" onclick="logEvent('TREATMENT_TAB_CLICKED','')"><a data-toggle="tab" href="#treatments">Treatments</a></li>
    <li onclick="logEvent('SIDE_EFFECTS_TAB_CLICKED','')"><a data-toggle="tab" href="#sideEffects">Side Effects</a></li>
    <li onclick="logEvent('FOLLOWUP_CARE_TAB_CLICKED','')"><a data-toggle="tab" href="#followUpCare">Follow up Care</a></li>
    <li onclick="logEvent('COMMUNITIES_TAB_CLICKED','')"><a data-toggle="tab" href="#community">Communities</a></li>
    <li onclick="logEvent('SYMPTOM_MANAGEMENT_TAB_CLICKED','')"><a data-toggle="tab" href="#symptomManagement">Symptom Management</a></li>
    <li onclick="logEvent('PREVENTIVE_CARE_TAB_CLICKED','')"><a data-toggle="tab" href="#preventiveCare">Preventive Care</a></li>
    <li onclick="logEvent('HEALTHY_BEHAVIOURS_TAB_CLICKED','')"><a data-toggle="tab" href="#healthyBehaviors">Healthy Behaviors</a></li>
    </ul>
    <script>
        console.log("test : ${person}"  )
    </script>
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
        <div id="preventiveCare" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "preventiveCare") }
        </div>
        <div id="healthyBehaviors" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "healthyBehaviors") }
        </div>
    </div>
    <% } %>
</div>
</body>
