${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<script type="text/javascript">
    jq(document).ready(function(){
        jq('#patientPortalJournals').addClass('active');

        jq(window).on('beforeunload', function(){   
            logEvent('unloading_my_posts_page','');
            return undefined;
        });
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">

    <% if(person.isPatient()) { %>
    ${ ui.includeFragment("patientportaltoolkit", "statusUpdater") }
    <br>
    <% } %>
    ${ ui.includeFragment("patientportaltoolkit", "feedItems") }
    </div>
</body>