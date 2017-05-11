${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalMyCancerBuddies').addClass('active');

    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    <% if (user.getPerson().isPatient()){%>

    ${ ui.includeFragment("patientportaltoolkit", "myCancerBuddiesProfileCard",[person: person]) }



    ${ ui.includeFragment("patientportaltoolkit", "myCancerBuddiesProfileThumbnails",[person: person]) }
    <% } %>

</div>
</body>