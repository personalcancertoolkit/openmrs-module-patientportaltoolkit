<script>
    jq(document).ready(function(){
        const currentProfileName = jq("#mycancerbuddiesname").text();
        const currentProfileDesc = jq("#mycancerbuddiesdescription").text();
        jq('#mycancerbuddiesSave').click(
        function () {
            const mycancerbuddiesname = jq("#mycancerbuddiesname").text();
            const mycancerbuddiesdescription = jq("#mycancerbuddiesdescription").text();

            if (mycancerbuddiesname.includes("Empty") || !(mycancerbuddiesname.trim())) {
                alert("Please provide a Screen Name");
            } else if(mycancerbuddiesname !== null || mycancerbuddiesname.includes("Empty") || mycancerbuddiesdescription != '') {

                logEvent('clicked_MyCancerBuddies_ProfileCard_save', JSON.stringify({
                    from: {
                        name: currentProfileName,
                        desc: currentProfileDesc,
                    },
                    to: {
                        name: mycancerbuddiesname,
                        desc: mycancerbuddiesdescription,
                    } 
                }));

                jq.get("myCancerBuddiesProfileCard/saveMyCancerBuddiesProfileCard.action", {
                    mycancerbuddiesname: mycancerbuddiesname,
                    mycancerbuddiesdescription: mycancerbuddiesdescription
                }).done(function() {
                    location.reload();
                });
            }
        });
        jq('#mycancerbuddiesRegister').click(
                function () {
                    logEvent('clicked_MyCancerBuddiesRegister','');
                    jq.get("myCancerBuddiesProfileCard/RegisterMyCancerBuddiesProfileCard.action")
                        .done(function() {
                            location.reload();
                        });
                });
    });
</script>
<% if (!personPreferences || !personPreferences.myCancerBuddies){%>
<div class="panel panel-primary">


    <div class="panel-heading">
        <h4 class="panel-title">Join MyCancerBuddies?</h4>
    </div>
    <div class="panel-body">
        <p>If you would like to participate in MyCancerBuddies, please click on Register</p>
    </div>
    <div class="panel-footer">
        <button id="mycancerbuddiesRegister" type="button" class="btn-primary btn-sm">Register</button>
    </div>
</div>
<% } else {%>
<div class="row" style="margin-top:1px;">
        <div class="col-xs-6">
            <div class="row well profile form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-xs-6" for="mycancerbuddiesnameh6">Screen Name: <i data-toggle="tooltip" data-placement="top" title="" data-original-title="The name you choose will be visible to other MyCancerBuddies Patients" class="glyphicon glyphicon-question-sign"></i>
                        </label>
                        <div class="col-xs-6"> <h6 id="mycancerbuddiesnameh6" data-toggle="tooltip" data-placement="top" title="" data-original-title="Click on this to edit how your name appears as to others"><p><strong id="mycancerbuddiesname" class="edit mycancerbuddies"> ${(personPreferences.myCancerBuddiesName)}</strong></p></h6></div>
                    </div>
                <div class="form-group">
                    <label class="control-label col-xs-6" for="mycancerbuddiesAgeh6">Age: </label>
                    <div class="col-xs-6">  <h6 id="mycancerbuddiesAgeh6" ><p id="mycancerbuddiesAge" ><% if (personPreferences.person.birthdate && !personPreferences.person.getBirthdate().is(null) ){ %>
                    <% if (person.age > 0) { %>
                    ${ui.message("coreapps.ageYears", personPreferences.person.age)}
                        <% }} %></p></h6></div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-6" for="mycancerbuddiesGenderh6">Gender:</label>
                    <div class="col-xs-6">  <h6 id="mycancerbuddiesGenderh6" ><p id="mycancerbuddiesGender" >${ui.message("coreapps.gender." + personPreferences.person.gender)}</p></h6></div>
                </div>
<div class="form-group">
    <label class="control-label col-xs-6" for="mycancerbuddiesdescriptionh6">Screen Description: <i data-toggle="tooltip" data-placement="top" title="" data-original-title="This is a short introduction about what you would like others to know about you" class="glyphicon glyphicon-question-sign"></i></label>
    <div class="col-xs-6">  <h6 id="mycancerbuddiesdescriptionh6"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Click on this to edit how your description appears as to others"><p id="mycancerbuddiesdescription" data-type="textarea" class="edit mycancerbuddies">${(personPreferences.myCancerBuddiesDescription)}</p></h6></div>
</div>
<div class="col-sm-8 pull-right">
    <button id="mycancerbuddiesSave" class="pull-right btn btn-primary btn-sm disabled" disabled>save</button>
</div>

</div>
</div>
</div>
<% } %>