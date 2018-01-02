${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalConnections').addClass('active');
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    <% if (!personPreferences || !personPreferences.myCancerBuddies){%>
    <% if (person.isPatient()){%>
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">MyCancerBuddies</h3>
        </div>
        <div class="panel-body">
            Would you like to join My CancerBuddies - to interact with patients in this program in similar situations?
        </div>
    </div>
    <% } %>
    <% } %>
<br>
${ ui.includeFragment("patientportaltoolkit", "connections/connections") }
</div>
</body>
