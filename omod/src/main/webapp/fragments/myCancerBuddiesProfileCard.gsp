<script>
    jq(document).ready(function(){
        jq('#mycancerbuddiesSave').click(
        function () {
            //console.log( jq("#mycancerbuddiesname").text()+"-------"+jq("#mycancerbuddiesdescription").text())
            if(jq("#mycancerbuddiesname").text() != null || jq("#mycancerbuddiesdescription").text() != '') {
                jq.get("myCancerBuddiesProfileCard/saveMyCancerBuddiesProfileCard.action", {
                    mycancerbuddiesname: jq("#mycancerbuddiesname").text(),
                    mycancerbuddiesdescription: jq("#mycancerbuddiesdescription").text()
                }, function () {
                });
                setTimeout(
                        function () {
                            location.reload();
                        }, 1000);
            }
        });
        jq('#mycancerbuddiesRegister').click(
                function () {
                        jq.get("myCancerBuddiesProfileCard/RegisterMyCancerBuddiesProfileCard.action", function () {
                        });
                        setTimeout(
                                function () {
                                    location.reload();
                                }, 1000);
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
                        <label class="control-label col-xs-4" for="mycancerbuddiesnameh6">Screen Name: </label>
                        <div class="col-xs-8"> <h6 id="mycancerbuddiesnameh6" data-toggle="tooltip" data-placement="top" title="" data-original-title="Click on this to edit how your name appears as to others"><p><strong id="mycancerbuddiesname" class="edit mycancerbuddies"> ${(personPreferences.myCancerBuddiesName)}</strong></p></h6></div>
                    </div>
<div class="form-group">
    <label class="control-label col-xs-4" for="mycancerbuddiesdescriptionh6">Screen Description: </label>
    <div class="col-xs-8">  <h6 id="mycancerbuddiesdescriptionh6"  data-toggle="tooltip" data-placement="top" title="" data-original-title="Click on this to edit how your description appears as to others"><p id="mycancerbuddiesdescription" data-type="textarea" class="edit mycancerbuddies">${(personPreferences.myCancerBuddiesDescription)}</p></h6></div>
</div>
<div class="col-sm-8 pull-right">
    <button id="mycancerbuddiesSave" class="pull-right btn btn-primary btn-sm disabled" disabled>save</button>
</div>

</div>
</div>
</div>
<% } %>