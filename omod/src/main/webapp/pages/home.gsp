${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalNavHome').addClass('active');
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">
    ${ ui.includeFragment("patientportaltoolkit", "profileHeader") }
    <ul class="nav nav-pills">
        <li class="active"><a data-toggle="tab" href="#journal">Journals</a></li>
        <li><a data-toggle="tab" href="#connections">Connections</a></li>
        <li><a data-toggle="tab" href="#treatments">Treatments</a></li>
        <li><a data-toggle="tab" href="#sideEffects">Side Effects</a></li>
    </ul>

    <div class="tab-content">
        <div id="journal" class="tab-pane fade in active">
            ${ ui.includeFragment("patientportaltoolkit", "statusUpdater") }
        </div>
        <div id="connections" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "connections") }
        </div>
        <div id="treatments" class="tab-pane fade">
            <h3>Menu 2</h3>
            <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
        </div>
        <div id="sideEffects" class="tab-pane fade">
            ${ ui.includeFragment("patientportaltoolkit", "sideEffects") }
        </div>
    </div>
</div>
</body>