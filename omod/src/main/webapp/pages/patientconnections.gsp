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
                <h3 class="panel-title">My Connections</h3>
            </div>
            <div class="panel-body">
                <p>
                Would you like to create an online relationship with a family member or caregiver?
                </p>
                <p>If so, click on "Add Connections" to invite them to create their own account 
                    in the SPHERE Personal Cancer Toolkit</p>
                <p>In the form that pops up, check the box for "Can see my Medical Profile" 
                    so that your new connection will be able to see the information in your own SPHERE account</p>
            </div>
        </div>
        <% } %>
    <% } else {%>
    <div class="panel panel-info">
        <div class="panel-heading">
                <h3 class="panel-title">CancerBuddies and family connections</h3>
            </div>
            <div class="panel-body">
                <p>
                CancerBuddies and family connections outside SPHERE  are listed below.
                </p>
                <p>Click "Add Connections" to invite more family members or caregivers to view your profile</p>
            </div>
        </div>
    <% } %>
<br>
${ ui.includeFragment("patientportaltoolkit", "connections/connections") }
</div>
</body>
