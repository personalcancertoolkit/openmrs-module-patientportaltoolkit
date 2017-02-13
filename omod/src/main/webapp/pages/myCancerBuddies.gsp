${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalMyCancerBuddies').addClass('active');

    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    <% if (user.getPerson().isPatient()){%>
    ${ ui.includeFragment("patientportaltoolkit", "addCancerBuddies") }

    ${ ui.includeFragment("patientportaltoolkit", "myCancerBuddiesProfileCard",[person: person]) }



    ${ ui.includeFragment("patientportaltoolkit", "myCancerBuddiesProfileThumbnails",[person: person]) }
    <% } %>

</div>
</body>