${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalJournals').addClass('active');
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    ${ ui.includeFragment("patientportaltoolkit", "statusUpdater") }
    <br>
    ${ ui.includeFragment("patientportaltoolkit", "feedItems") }
    </div>
</body>